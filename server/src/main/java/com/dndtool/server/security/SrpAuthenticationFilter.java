package com.dndtool.server.security;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;

@Component
public class SrpAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final CredentialsService credentialsService;
    private final ObjectMapper jsonMapper;

    @Autowired
    public SrpAuthenticationFilter(CredentialsService credentialsService) {
        super("/login");
        this.credentialsService = credentialsService;
        this.jsonMapper = new ObjectMapper();
        setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        SRP6ServerSession srpSession = (SRP6ServerSession) request.getSession().getAttribute("srpSession");
        Authentication srpAuth;
        if (srpSession == null) {
            SRP6CryptoParams srpParams = SRP6CryptoParams.getInstance(1024, "SHA-256");
            srpSession = new SRP6ServerSession(srpParams);

            String username = request.getParameter("username");
            if (username == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("username must be specified");
                response.getWriter().close();
            }
            else {
                CredentialsDTO credentials = credentialsService.getUser(request.getParameter("username"));
                BigInteger serverPublic;
                if (credentials == null) {
                    SecureRandom random = new SecureRandom();
                    byte[] randomSalt = new byte[16];
                    random.nextBytes(randomSalt);
                    serverPublic = srpSession.mockStep1(
                        username, new BigInteger(randomSalt), new BigInteger(new byte[]{1, 2, 3}));
                }
                else {
                    BigInteger userSalt = credentialsService.getVerificationSalt(credentials)
                        .orElseThrow(() -> new RuntimeException("Could not retrieve user salt"));
                    BigInteger userSecret = credentialsService.getVerificationSecret(credentials)
                        .orElseThrow(() -> new RuntimeException("Could not retrieve user secret"));
                    serverPublic = srpSession.step1(username, userSalt, userSecret);
                }
                ServerLoginEvidence serverEvidence = new ServerLoginEvidence(serverPublic.toString(16));
                OutputStream output = response.getOutputStream();
                jsonMapper.writeValue(response.getOutputStream(), serverEvidence);
                output.close();
            }
            srpAuth = null;
        }
        else {
            ClientLoginEvidence clientEvidence = jsonMapper.readValue(
                request.getInputStream(), ClientLoginEvidence.class);
            try {
                srpSession.step2(
                    new BigInteger(clientEvidence.getClientPublicValHex(), 16),
                    new BigInteger(clientEvidence.getClientEvidenceHex(), 16));
            }
            catch (SRP6Exception ex) {
                throw new BadCredentialsException("Failed client evidence validation", ex);
            }
            srpAuth = null;
        }

        return srpAuth;
    }
}
