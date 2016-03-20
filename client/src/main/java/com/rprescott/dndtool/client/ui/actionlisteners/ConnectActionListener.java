package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.rprescott.dndtool.client.connection.ConnectionService;
import com.rprescott.dndtool.client.ui.authentication.AuthenticationScreen;

public class ConnectActionListener implements ActionListener {
    
    private JTextField hostName;
    private JTextField port;
    private JFrame component;
    
    public ConnectActionListener(JFrame component, JTextField hostName, JTextField port) {
        this.component = component;
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ConnectionService.obtainConnection(hostName.getText(), Integer.valueOf(port.getText()));
            JOptionPane.showMessageDialog(component, "Connection successfully established.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // We've successfully connected. Now prompt the user for authentication details.
            new AuthenticationScreen();
            component.dispose();
        }
        catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(component, "Unable to resolve host. Ensure the IP Address is correct.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        catch (ConnectException ex) {
            JOptionPane.showMessageDialog(component, "Connection was refused by host. Ensure the server is running and you're connecting to a port the host has forwarded.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(component, "Port must be between 1 and 65535.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(component, "Some janky ass shit went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}
