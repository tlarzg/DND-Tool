package com.rprescott.dndtool.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.rprescott.dndtool.server.service.login.LoginService;
import com.rprescott.dndtool.sharedmessages.login.LoginCredentials;
import com.rprescott.dndtool.sharedmessages.login.LoginResponse;

/**
 * Class to perform the work the client requests. There will be multiple instances of this
 * class each representing a connection to a separate client.
 */
public class ClientWorkerThread implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private LoginService loginService;

    /**
     * Creates a new instance of this class given a supplied client Socket.
     * 
     * @param loginService - TODO: Figure out better way to Inject this. Since new ClientWorkerThread() is invoked, default Autowiring 
     *                       doesn't work because it will override Spring's injected service. Right now, the singleton is injecting its
     *                       login service, but when this class requires more services, the constructor will grow quite large.
     * @param clientSocket - The socket representing the client this instance is connected to.
     */
    public ClientWorkerThread(LoginService loginService, Socket clientSocket) {
        Thread.currentThread().setName("Client Worker Thread " + clientSocket.getInetAddress().getHostName() +  "  " + this.hashCode());
        
        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.loginService = loginService;
            this.clientSocket = clientSocket;
            this.clientSocket.setKeepAlive(true);
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
    private void delegateWork(Object objectFromClient) {
        if (objectFromClient instanceof LoginCredentials) {
            performLogonRequest((LoginCredentials) objectFromClient);
        }
        else {
            System.err.println("Unknown object received by client.");
        }
    }

    /**
     * Performs a logon request. Checks the credentials supplied by the client against stored
     * credentials. If the user name does not exist or the user name / password combination is incorrect,
     * a negative response is sent back to the client.
     * 
     * @param objectFromClient - The credentials supplied by the client.
     */
    private void performLogonRequest(LoginCredentials objectFromClient) {
        String userName = ((LoginCredentials) objectFromClient).getUserName();
        char[] password = ((LoginCredentials) objectFromClient).getPassword();
        System.out.println("Received login request for User: " + userName);
        if (loginService.userExists(userName)) {
            try {
                if (loginService.validateUser(userName, password)) {
                    outputStream.writeObject(new LoginResponse(true));
                    outputStream.flush();
                }
                else {
                    outputStream.writeObject(new LoginResponse(false));
                    outputStream.flush();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.println("User did not exist.");
            try {
                outputStream.writeObject(new LoginResponse(false));
                outputStream.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
