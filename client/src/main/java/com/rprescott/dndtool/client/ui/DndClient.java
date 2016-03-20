package com.rprescott.dndtool.client.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.rprescott.dndtool.client.ui.actionlisteners.ConfigureActionListener;

import net.miginfocom.swing.MigLayout;

public class DndClient extends JFrame {
    
    private static final long serialVersionUID = -6579165759652742470L;

    /** The instance of this singleton. */
    private static DndClient instance;
    
    private JPanel contentPanel;
    
    private static final String VERSION = "0.1";
    
    public DndClient() {
        if (instance == null) {
            instance = this;
            launch();
            createMenuBar();
        }
    }

    public void launch() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new MigLayout());
        this.setTitle("DND Client - " + VERSION);
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenDimensions.getWidth() * 0.75), (int) (screenDimensions.getHeight() * 0.75));
        System.out.println("Client Width: " + this.getSize().width);
        System.out.println("Client Height: " + this.getSize().height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(contentPanel);
        this.setVisible(true);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu connectionOptions = createMenuOption("Connection Options");
        JMenuItem configure = new JMenuItem("Configure");
        configure.addActionListener(new ConfigureActionListener());
        connectionOptions.add(configure);
        menuBar.add(connectionOptions);
        this.setJMenuBar(menuBar);
    }

    private JMenu createMenuOption(String name) {
        return new JMenu(name);
    }
    
    public static DndClient getInstance() {
        return instance;
    }
    
    public void setMainContent(Component content) {
        this.contentPanel.add(content);
    }
}
