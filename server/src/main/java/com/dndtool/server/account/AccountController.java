package com.dndtool.server.account;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;

//XXX: Move login stuff to a security filter
@Controller
public class AccountController {

    private final AccountService accountService;
    private Optional<SRP6ServerSession> srpSession = Optional.empty();

    @Autowired
    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/login/start", method = RequestMethod.POST)
    @ResponseBody
    public LoginChallenge startLogin(@RequestBody LoginRequest request)
        throws MissingCredentialsException {

        String userName = request.getUserName();
        System.out.println(userName);
        AccountCredentialsDTO credentials = accountService.getUser(userName);

        if (credentials == null) {
            throw new MissingCredentialsException();
        }

        SRP6CryptoParams srpConfig = SRP6CryptoParams.getInstance();
        srpSession = Optional.of(new SRP6ServerSession(srpConfig));

        BigInteger userSalt = accountService.getVerificationSalt(credentials).orElseThrow(
            () -> new RuntimeException("Could not obtain verification salt"));
        BigInteger verificationSecret = accountService.getVerificationSecret(credentials).orElseThrow(
            () -> new RuntimeException("Could not obtain verification secret"));
        BigInteger serverPublicVal =
            srpSession.get().step1(userName, userSalt, verificationSecret);

        return new LoginChallenge(userSalt.toString(16), serverPublicVal.toString(16));
    }

    @RequestMapping(value = "/login/response", method = RequestMethod.POST)
    @ResponseBody
    public ServerLoginEvidence clientEvidenceResponse(ClientLoginEvidence clientEvidence)
        throws MissingCredentialsException, SRP6Exception {

        if (!srpSession.isPresent()) {
            throw new MissingCredentialsException();
        }

        try {
            BigInteger serverEvidence = srpSession.get().step2(
                new BigInteger(clientEvidence.getClientPublicValHex(), 16),
                new BigInteger(clientEvidence.getClientEvidenceHex(), 16));
            System.out.println("Successfully authenticated user");
            return new ServerLoginEvidence(serverEvidence.toString(16));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Converts any {@link SRP6Exception}s thrown by methods in this controller to return a
     * {@link HttpStatus#UNAUTHORIZED UNAUTHORIZED} status.
     */
    @ResponseStatus(value=HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SRP6Exception.class)
    public void invalidAuth() {
    }

    /**
     * Exception thrown from this controller when a user is requesting to login with missing/unregistered credentials.
     * <p>Automatically translated to an {@link HttpStatus#UNAUTHORIZED UNAUTHORIZED} return status code.
     */
    @ResponseStatus(value=HttpStatus.UNAUTHORIZED)
    private static class MissingCredentialsException extends Exception {
        private static final long serialVersionUID = 6047124186787754201L;
    }
}
