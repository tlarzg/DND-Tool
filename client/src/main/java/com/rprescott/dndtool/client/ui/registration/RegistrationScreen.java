package com.rprescott.dndtool.client.ui.registration;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.rprescott.dndtool.client.Constants;
import com.rprescott.dndtool.client.ui.actionlisteners.RegisterNewUserActionListener;

import net.miginfocom.swing.MigLayout;

public class RegistrationScreen extends JFrame {

    private static final long serialVersionUID = -7667928221798082456L;
    private MigLayout layout;
    private JPanel contentPanel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    
    public RegistrationScreen() {
        contentPanel = new JPanel();
        layout = new MigLayout();
        contentPanel.setLayout(layout);
        addContent();
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle(Constants.VERSION);
        this.setSize((int) (screenDimensions.getWidth() * 0.15), (int) (screenDimensions.getHeight() * 0.15));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(contentPanel);
        this.setVisible(true);
    }

    private void addContent() {
        addUserNameSection();
        addPasswordSection();
        addRegisterSection();
    }

    private void addUserNameSection() {
        JLabel userNameLabel = new JLabel("Username: ");
        userNameField = new JTextField(15);
        contentPanel.add(userNameLabel);
        contentPanel.add(userNameField, "wrap");
    }

    private void addPasswordSection() {
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(15);
        contentPanel.add(passwordLabel);
        contentPanel.add(passwordField, "wrap");
    }
    
    private void addRegisterSection() {
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterNewUserActionListener(this, userNameField, passwordField));
        contentPanel.add(registerButton);
    }

}
