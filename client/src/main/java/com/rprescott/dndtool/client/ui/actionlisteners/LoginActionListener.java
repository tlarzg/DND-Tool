package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.rprescott.dndtool.sharedmessages.login.LoginCredentials;

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
    public void actionPerformed(ActionEvent e) {
        // TODO: This is a bad bad way to do passwords. This is temporary just to get objects serialized across things.
        //       ... I'll salt and hash it eventually.
        LoginCredentials credentials = new LoginCredentials(userNameField.getText(), new String(passwordField.getPassword()));
        System.out.println("Submitting login request with Username: " + credentials.getUserName() + " and Password: " + credentials.getPassword());
    }

}
