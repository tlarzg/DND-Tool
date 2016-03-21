package com.rprescott.dndtool.client.ui.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.rprescott.dndtool.client.ui.registration.RegistrationScreen;

public class RegisterButtonActionListener implements ActionListener {
    
    private JFrame frame;
    
    public RegisterButtonActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new RegistrationScreen();
        frame.dispose();
    }

}
