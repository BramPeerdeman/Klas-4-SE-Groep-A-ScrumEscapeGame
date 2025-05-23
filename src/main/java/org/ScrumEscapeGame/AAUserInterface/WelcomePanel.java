package org.ScrumEscapeGame.AAUserInterface;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel {
    private final GameUIService uiService;

    public WelcomePanel(GameUIService uiService) {
        this.uiService = uiService;
    }

    // The welcome panel with a start message.
    public void initWelcomePanel(JPanel welcome) {
        JLabel msg = new JLabel(
                "<html><h1>Welcome to Scrum Escape Game</h1>"
                        + "<p>Press START or ENTER to begin.</p></html>",
                SwingConstants.CENTER
        );
        JButton start = new JButton("START");
        start.addActionListener(e -> {
            uiService.showGamePanel();
            uiService.beginGame();
        });
        // Set the default button so that ENTER starts the game.
        getRootPane(welcome).setDefaultButton(start);
        welcome.setLayout(new BorderLayout());
        welcome.add(msg, BorderLayout.CENTER);
        welcome.add(start, BorderLayout.SOUTH);
    }

    private JRootPane getRootPane(JPanel panel) {
        // Helper to get the JRootPane for setting a default button.
        return SwingUtilities.getRootPane(panel);
    }
}

