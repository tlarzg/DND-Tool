package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nimbusds.srp6.SRP6ClientCredentials;
import com.nimbusds.srp6.SRP6ClientSession;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.rprescott.dndtool.client.connection.ConnectionService;
import com.rprescott.dndtool.sharedmessages.login.ClientLoginEvidence;
import com.rprescott.dndtool.sharedmessages.login.InitiateLogin;
import com.rprescott.dndtool.sharedmessages.login.InitiateLoginResponse;
import com.rprescott.dndtool.sharedmessages.login.LoginResponse;
import com.rprescott.dndtool.sharedmessages.login.ServerLoginEvidence;

/**
 * Action listener to validate credentials against the stored server credentials.
 */
public class LoginActionListener implements ActionListener {

    private JTextField userNameField;
    private JPasswordField passwordField;
    private JFrame component;

    public LoginActionListener(JFrame component, JTextField userName, JPasswordField password) {
        this.component = component;
        this.userNameField = userName;
        this.passwordField = password;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String userName = userNameField.getText();
        System.out.println("Submitting login request with Username: " + userName);

        SRP6ClientSession srpClient = initiateLogin(userName, new String(passwordField.getPassword()));

        InitiateLoginResponse initiateResponse = getResponse(InitiateLoginResponse.class);
        if (initiateResponse != null) {
            BigInteger serverPublic = new BigInteger(initiateResponse.getServerPublicHex(), 16);
            BigInteger salt = new BigInteger(initiateResponse.getSaltHex(), 16);

            SRP6CryptoParams config = SRP6CryptoParams.getInstance();
            try {
                SRP6ClientCredentials srpCredentials = srpClient.step2(config, salt, serverPublic);
                sendClientEvidence(srpCredentials);

                ServerLoginEvidence serverEvidenceResponse = getResponse(ServerLoginEvidence.class);
                if (serverEvidenceResponse != null) {
                    BigInteger serverEvidence = new BigInteger(serverEvidenceResponse.getServerEvidenceHex(), 16);
                    try {
                        srpClient.step3(serverEvidence);

                        LoginResponse response = getResponse(LoginResponse.class);
                        if (response != null) {
                            if (response.isLoginSuccessful()) {
                                JOptionPane.showMessageDialog(component, "Authentication Successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                JOptionPane.showMessageDialog(component, "Authentication Failed. Make sure the username/password entered is correct.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    catch (SRP6Exception ex) {
                        JOptionPane.showMessageDialog(component, "Received invalid SRP evidence from server.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Received invalid server evidence");
                    }
                }
            }
            catch (SRP6Exception ex) {
                JOptionPane.showMessageDialog(component, "Received invalid SRP public val from server.", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Received invalid server public val");
            }
        }
    }

    private <T> T getResponse(Class<T> clazz) {
        T result;
        try {
            Object response = ConnectionService.obtainClientInputStream().readObject();
            if (clazz.isAssignableFrom(response.getClass())) {
                result = clazz.cast(response);
            }
            else {
                JOptionPane.showMessageDialog(component, "Received unexpected response from server.", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println(String.format("Received unexpected response type. Got: %s, Expected %s",
                    response.getClass().getSimpleName(), clazz.getSimpleName()));
                result = null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            result = null;
        }
        return result;
    }

    private SRP6ClientSession initiateLogin(String userName, String password) {
        SRP6ClientSession srpClient = new SRP6ClientSession();
        srpClient.step1(userName, password);

        InitiateLogin loginStep1 = new InitiateLogin(userName);
        try {
            ConnectionService.obtainClientOutputStream().writeObject(loginStep1);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return srpClient;
    }

    private void sendClientEvidence(SRP6ClientCredentials credentials) {
        ClientLoginEvidence evidence = new ClientLoginEvidence(credentials.A.toString(16), credentials.M1.toString(16));
        try {
            ConnectionService.obtainClientOutputStream().writeObject(evidence);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
