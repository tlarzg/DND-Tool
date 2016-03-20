package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.rprescott.dndtool.client.ConnectionService;

public class ConnectActionListener implements ActionListener {
    
    private String hostName;
    private int port;
    
    public ConnectActionListener(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ConnectionService.obtainConnection(hostName, port);
    }

}
