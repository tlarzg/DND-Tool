package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.rprescott.dndtool.client.connection.ConnectionService;
import com.rprescott.dndtool.sharedmessages.login.LoginCredentials;
import com.rprescott.dndtool.sharedmessages.login.LoginResponse;

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
        LoginCredentials credentials = new LoginCredentials(userNameField.getText(), passwordField.getPassword());
        System.out.println("Submitting login request with Username: " + credentials.getUserName());
        try {
            ConnectionService.obtainClientOutputStream().writeObject(credentials);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        
        // Let's see if we were able to login successfully.
        try {
            Object response = ConnectionService.obtainClientInputStream().readObject();
            // Make sure we're receiving the correct response.
            if (response instanceof LoginResponse) {
                if (((LoginResponse) response).isLoginSuccessful()) {
                    JOptionPane.showMessageDialog(component, "Authentication Successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(component, "Authentication Failed. Make sure the username/password entered is correct.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(component, "Received unexpected response from server.", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Received unexpected response type.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
