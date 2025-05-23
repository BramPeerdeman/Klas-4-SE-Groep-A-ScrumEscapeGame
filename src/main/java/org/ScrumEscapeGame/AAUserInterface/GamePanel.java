package org.ScrumEscapeGame.AAUserInterface;


import org.ScrumEscapeGame.Handlers.KeyBindSetup;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final GameUIService uiService;
    private final KeyBindSetup keyBindSetup;
    private MapPanel mapPanel;
    private JTextField inputField;

    public GamePanel(GameUIService uiService, KeyBindSetup keyBindSetup) {
        this.uiService = uiService;
        this.keyBindSetup = keyBindSetup;
        // Initialize your components, potentially using information from uiService.
        initGamePanel();
    }

    private void initGamePanel() {
        setLayout(new BorderLayout());

        // --- Left Side: Status and Map ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(250, 150));
        JLabel statusLabel = new JLabel("Player Status: " + uiService.getPlayer().getStatus());
        statusPanel.add(statusLabel);
        leftPanel.add(statusPanel, BorderLayout.NORTH);

        // Initialize MapPanel with required data (passed directly from the uiService if necessary)
        mapPanel = new MapPanel(uiService.getRooms(), uiService.getPlayer());
        leftPanel.add(mapPanel, BorderLayout.CENTER);

        // --- Right/Center: Output and Input ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        JTextArea outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        mainPanel.add(outputScroll, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(e -> {
            String cmd = inputField.getText().trim().toLowerCase();
            // Now you can delegate command handling via a controller or even uiService if it knows about it.
            // For example: uiService.processCommand(cmd);
            inputField.setText("");
        });
        mainPanel.add(inputField, BorderLayout.SOUTH);

        // Assemble overall UI
        JPanel overallGamePanel = new JPanel(new BorderLayout());
        overallGamePanel.add(leftPanel, BorderLayout.WEST);
        overallGamePanel.add(mainPanel, BorderLayout.CENTER);

        // Setup key bindings using the provided service/method.
        keyBindSetup.setupGlobalKeyBindings(overallGamePanel);

        add(overallGamePanel);
        SwingUtilities.invokeLater(() -> overallGamePanel.requestFocusInWindow());
    }
}

