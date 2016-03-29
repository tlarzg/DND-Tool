package com.dndtool.server.security;

import java.io.File;
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

        System.err.println(request.getServletContext().getRealPath(File.separator));
        SRP6ServerSession srpSession = (SRP6ServerSession) request.getSession().getAttribute("srpSession");
        Authentication srpAuth;
        if (srpSession == null) {
            try {
                LoginRequest loginRequest = jsonMapper.readValue(request.getInputStream(), LoginRequest.class);
                request.getSession().setAttribute("srpSession", loginStep1(loginRequest.getUserName(), response));
            }
            catch (IOException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ex.printStackTrace();
            }
            srpAuth = null;
        }
        else {
            try {
                ClientLoginEvidence clientEvidence = jsonMapper.readValue(
                    request.getInputStream(), ClientLoginEvidence.class);
                loginStep2(srpSession, clientEvidence, response);
            }
            catch (IOException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ex.printStackTrace();
            }
            srpAuth = null;
        }

        return srpAuth;
    }

    private SRP6ServerSession loginStep1(String username, HttpServletResponse response) throws IOException {
        SRP6CryptoParams srpParams = SRP6CryptoParams.getInstance(1024, "SHA-256");
        SRP6ServerSession srpSession = new SRP6ServerSession(srpParams);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("username must be specified");
            response.getWriter().close();
        }
        else {
            CredentialsDTO credentials = credentialsService.getUser(username);
            BigInteger serverPublic;
            BigInteger userSalt;
            if (credentials == null) {
                SecureRandom random = new SecureRandom();
                byte[] randomSalt = new byte[16];
                random.nextBytes(randomSalt);
                userSalt = new BigInteger(randomSalt);
                serverPublic = srpSession.mockStep1(
                    username, userSalt, new BigInteger(new byte[]{1, 2, 3}));
            }
            else {
                userSalt = credentialsService.getVerificationSalt(credentials)
                    .orElseThrow(() -> new RuntimeException("Could not retrieve user salt"));
                BigInteger userSecret = credentialsService.getVerificationSecret(credentials)
                    .orElseThrow(() -> new RuntimeException("Could not retrieve user secret"));
                serverPublic = srpSession.step1(username, userSalt, userSecret);
            }
            LoginChallenge challenge = new LoginChallenge(userSalt.toString(16),
                serverPublic.toString(16), srpParams.g.toString(16), srpParams.N.toString(16));
            OutputStream output = response.getOutputStream();
            jsonMapper.writeValue(response.getOutputStream(), challenge);
            output.close();
        }
        return srpSession;
    }

    private void loginStep2(SRP6ServerSession srpSession, ClientLoginEvidence clientEvidence,
        HttpServletResponse response) {

        try {
            srpSession.step2(
                new BigInteger(clientEvidence.getClientPublicValHex(), 16),
                new BigInteger(clientEvidence.getClientEvidenceHex(), 16));
        }
        catch (SRP6Exception ex) {
            throw new BadCredentialsException("Failed client evidence validation", ex);
        }
    }
}
