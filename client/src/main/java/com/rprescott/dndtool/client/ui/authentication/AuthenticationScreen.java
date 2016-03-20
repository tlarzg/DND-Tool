package com.rprescott.dndtool.client.ui.authentication;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.rprescott.dndtool.client.Constants;
import com.rprescott.dndtool.client.ui.actionlisteners.LoginActionListener;

import net.miginfocom.swing.MigLayout;

public class AuthenticationScreen extends JFrame {

    private static final long serialVersionUID = 3194339558688311620L;
    private JPanel contentPanel;
    private MigLayout layout;
    private JTextField userNameField;
    private JPasswordField passwordField;
    
    public AuthenticationScreen() {
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
        addEnterButtonSection();
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
    
    private void addEnterButtonSection() {
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginActionListener(this, userNameField, passwordField));
        contentPanel.add(loginButton);
    }

}
