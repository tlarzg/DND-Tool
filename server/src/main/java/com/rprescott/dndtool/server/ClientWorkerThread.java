package com.rprescott.dndtool.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;
import com.rprescott.dndtool.server.database.login.CredentialDTO;
import com.rprescott.dndtool.server.service.login.UserService;
import com.rprescott.dndtool.sharedmessages.login.ClientLoginEvidence;
import com.rprescott.dndtool.sharedmessages.login.InitiateLogin;
import com.rprescott.dndtool.sharedmessages.login.InitiateLoginResponse;
import com.rprescott.dndtool.sharedmessages.login.LoginResponse;
import com.rprescott.dndtool.sharedmessages.login.ServerLoginEvidence;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistration;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistrationResponse;

/**
 * Class to perform the work the client requests. There will be multiple instances of this
 * class each representing a connection to a separate client.
 */
public class ClientWorkerThread implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageDelegator messageDelegator;
    private Optional<SRP6ServerSession> srpServerSession;

    /**
     * Creates a new instance of this class given a supplied client Socket.
     *
     * @param loginService - TODO: Figure out better way to Inject this. Since new ClientWorkerThread() is invoked, default Autowiring
     *                       doesn't work because it will override Spring's injected service. Right now, the singleton is injecting its
     *                       login service, but when this class requires more services, the constructor will grow quite large.
     * @param clientSocket - The socket representing the client this instance is connected to.
     */
    public ClientWorkerThread(Socket clientSocket) {
        Thread.currentThread().setName("Client Worker Thread " + clientSocket.getInetAddress().getHostName() +  "  " + this.hashCode());

        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.clientSocket = clientSocket;
            this.clientSocket.setKeepAlive(true);
            this.srpServerSession = Optional.empty();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
            // Just convert the first line of the input into a String and print it out onto the server console.
            while (true) {
                System.out.println("Executing on thread: " + Thread.currentThread().getName());
                Object objectFromClient = input.readObject();
                delegateWork(objectFromClient);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Based on the object received from the client, delegates the work to the appropriate helper.
     *
     * @param objectFromClient - The object received by the client.
     */
    private void delegateWork(Object objectFromClient) throws IOException {
        // TODO: I'm not too happy with how this works yet. The processors don't do the work (yet).
        messageDelegator.delegateWork(objectFromClient);
        if (objectFromClient instanceof InitiateLogin) {
            if (!srpServerSession.isPresent()) {
                srpServerSession = Optional.of(initiateLogon((InitiateLogin) objectFromClient));
            }
            else {
                // Trying to logon when already logged in/in the process of logging in
                clientSocket.close();
            }
        }
        else if (objectFromClient instanceof ClientLoginEvidence) {
            if (srpServerSession.isPresent()) {
                respondWithServerEvidence(srpServerSession.get(), (ClientLoginEvidence) objectFromClient);
            }
            else {
                // Failed to generate/verify SRP evidence
                clientSocket.close();
            }
        }
        else if (objectFromClient instanceof NewUserRegistration) {
            performUserRegistrationRequest((NewUserRegistration) objectFromClient);
        }
        else {
            System.err.println("Unknown object received by client.");
        }
    }

    private SRP6ServerSession initiateLogon(InitiateLogin loginRequest) throws IOException {
        SRP6CryptoParams srpConfig = SRP6CryptoParams.getInstance();
        SRP6ServerSession srpSession = new SRP6ServerSession(srpConfig);

        CredentialDTO userCredentials = userService.getUser(loginRequest.getUserName());
        if (userCredentials != null) {
            BigInteger userSalt = userService.getVerificationSalt(userCredentials).orElseThrow(
                () -> new RuntimeException("Could not obtain verification salt"));
            BigInteger verificationSecret = userService.getVerificationSecret(userCredentials).orElseThrow(
                () -> new RuntimeException("Could not obtain verification secret"));

            BigInteger serverPublicVal = srpSession.step1(loginRequest.getUserName(), userSalt, verificationSecret);

            InitiateLoginResponse initiateResponse = new InitiateLoginResponse(
                userSalt.toString(16), serverPublicVal.toString(16));
            outputStream.writeObject(initiateResponse);
            outputStream.flush();
        }

        return srpSession;
    }

    private void respondWithServerEvidence(SRP6ServerSession srpSession, ClientLoginEvidence clientEvidence)
        throws IOException {

        try {
            System.out.println("Received client evidence: " + clientEvidence);
            BigInteger serverEvidence = srpSession.step2(
                new BigInteger(clientEvidence.getClientPublicHex(), 16),
                new BigInteger(clientEvidence.getClientEvidenceHex(), 16));

            ServerLoginEvidence evidenceResponse = new ServerLoginEvidence(serverEvidence.toString(16));
            outputStream.writeObject(evidenceResponse);
            outputStream.flush();

            LoginResponse loginResponse = new LoginResponse(true);
            outputStream.writeObject(loginResponse);
            outputStream.flush();
            System.out.println("Successully negotiated login for: " + srpSession.getUserID());
        }
        catch (SRP6Exception ex) {
            System.err.println("Client evidence verification failed");
            throw new RuntimeException(ex);
        }
    }

    private void performUserRegistrationRequest(NewUserRegistration registrationRequest) {
        System.out.println("Received registration request: " + registrationRequest);
        BigInteger verificationSecret = new BigInteger(registrationRequest.getVerificationSecretHex(), 16);
        BigInteger verificationSalt = new BigInteger(registrationRequest.getVerificationSaltHex(), 16);

        userService.registerUser(registrationRequest.getUserName(), verificationSalt, verificationSecret);

        NewUserRegistrationResponse response = new NewUserRegistrationResponse(true);
        try {
            outputStream.writeObject(response);
            outputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
