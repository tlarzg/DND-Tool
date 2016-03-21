package com.rprescott.dndtool.client.ui.login;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import com.rprescott.dndtool.client.Constants;
import com.rprescott.dndtool.client.connection.IpChecker;
import com.rprescott.dndtool.client.ui.actionlisteners.ConnectActionListener;
import com.rprescott.dndtool.client.ui.documentfilters.NumericDocumentFilter;

import net.miginfocom.swing.MigLayout;

public class LoginScreen extends JFrame {

    private static final long serialVersionUID = 6893583669278934306L;
    private JPanel contentPanel;
    private MigLayout layout;
    private JTextField hostnameTextField;
    private JTextField portTextField;
    
    public LoginScreen() {
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
        addHostnameSection();
        addPortSection();
        addConnectButton();
    }

    private void addHostnameSection() {
        JLabel hostnameLabel = new JLabel("Hostname: ");
        hostnameTextField = new JTextField(15);
        // Set the default text of the hostname to be the local network's external IP address.
        hostnameTextField.setText(IpChecker.getIp());
        contentPanel.add(hostnameLabel);
        contentPanel.add(hostnameTextField, "wrap");
    }

    private void addPortSection() {
        JLabel portLabel = new JLabel("Port: ");
        portTextField = new JTextField(15);
        portTextField.setText("1337");
        ((AbstractDocument) portTextField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        contentPanel.add(portLabel);
        contentPanel.add(portTextField, "wrap");
    }
    
    private void addConnectButton() {
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ConnectActionListener(this, hostnameTextField, portTextField));
        contentPanel.add(connectButton);
    }
}
