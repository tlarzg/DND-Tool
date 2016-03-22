package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6VerifierGenerator;
import com.rprescott.dndtool.client.connection.ConnectionService;
import com.rprescott.dndtool.client.ui.authentication.AuthenticationScreen;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistration;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistrationResponse;

public class RegisterNewUserActionListener implements ActionListener {

    private JFrame frame;
    private JTextField userNameField;
    private JPasswordField passwordField;

    public RegisterNewUserActionListener(JFrame frame, JTextField userNameField, JPasswordField passwordField) {
        this.frame = frame;
        this.userNameField = userNameField;
        this.passwordField = passwordField;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        SRP6CryptoParams srpConfig = SRP6CryptoParams.getInstance();
        SRP6VerifierGenerator srpGenerator = new SRP6VerifierGenerator(srpConfig);

        BigInteger salt = new BigInteger(SRP6VerifierGenerator.generateRandomSalt());
        BigInteger verificationSecret = srpGenerator.generateVerifier(
            salt, userNameField.getText(), new String(passwordField.getPassword()));

        NewUserRegistration registration = new NewUserRegistration(
                userNameField.getText(), verificationSecret.toString(16), salt.toString(16));
        System.out.println("Submitting registration request with Username: " + registration.getUserName());

        try {
            ConnectionService.obtainClientOutputStream().writeObject(registration);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

        // Let's see if we were able to register the new account successfully.
        try {
            Object response = ConnectionService.obtainClientInputStream().readObject();
            // Make sure we're receiving the correct response.
            if (response instanceof NewUserRegistrationResponse) {
                if (((NewUserRegistrationResponse) response).isRegistrationSuccessful()) {
                    // Registration was successful. Inform the user and take them back to the login screen.
                    JOptionPane.showMessageDialog(frame, "Account " + userNameField.getText() + " has been successfully registered", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new AuthenticationScreen();
                    frame.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Failed to create Account " + userNameField.getText(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(frame, "Received unexpected response from server.", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Received unexpected response type.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
