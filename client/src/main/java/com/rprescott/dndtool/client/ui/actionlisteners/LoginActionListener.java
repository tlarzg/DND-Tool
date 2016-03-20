package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
        String userName = userNameField.getText();
        // TODO: This is bad bad bad. This is temporary just to get objects serialized across things.
        String password = new String(passwordField.getPassword());
        System.out.println("Submitting login request with Username: " + userName + " and Password: " + password);
    }

}
