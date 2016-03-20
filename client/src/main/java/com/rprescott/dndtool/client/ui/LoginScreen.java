package com.rprescott.dndtool.client.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import com.rprescott.dndtool.client.IpChecker;
import com.rprescott.dndtool.client.ui.actionlisteners.ConnectActionListener;
import com.rprescott.dndtool.client.ui.documentfilters.NumericDocumentFilter;

import net.miginfocom.swing.MigLayout;

public class LoginScreen extends JPanel {

    private static final long serialVersionUID = 6893583669278934306L;
    private MigLayout layout;
    private JTextField hostnameTextField;
    private JTextField portTextField;
    
    public LoginScreen() {
        layout = new MigLayout();
        this.setLayout(layout);
        addContent();
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
        this.add(hostnameLabel);
        this.add(hostnameTextField, "wrap");
    }

    private void addPortSection() {
        JLabel portLabel = new JLabel("Port: ");
        portTextField = new JTextField(15);
        portTextField.setText("8080");
        ((AbstractDocument) portTextField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        this.add(portLabel);
        this.add(portTextField, "wrap");
    }
    
    private void addConnectButton() {
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ConnectActionListener(hostnameTextField.getText(), Integer.valueOf(portTextField.getText())));
        this.add(connectButton);
    }
}
