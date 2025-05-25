package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.GameBeginEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The WelcomePanel is the introductory screen of the Scrum Escape Game.
 * It presents a welcome message and allows the player to begin the game
 * by clicking a start button or pressing any key.
 */
public class WelcomePanel {
    private final GameUIService uiService;

    /**
     * Constructs a WelcomePanel.
     *
     * @param uiService The game UI service for panel switching and event publishing.
     */
    public WelcomePanel(GameUIService uiService) {
        this.uiService = uiService;
    }

    /**
     * Initializes the welcome panel, adding a message and a start button.
     * Allows players to begin the game via button click or key press.
     *
     * @param welcome The JPanel to be initialized.
     */
    public void initWelcomePanel(JPanel welcome) {
        // Ensure the panel can receive focus.
        welcome.setFocusable(true);
        SwingUtilities.invokeLater(welcome::requestFocusInWindow);

        // Create the welcome message.
        JLabel msg = new JLabel(
                "<html><h1>Welcome to Scrum Escape Game</h1><p>Press any key to begin.</p></html>",
                SwingConstants.CENTER
        );

        // Create a start button.
        JButton start = new JButton("START");
        start.addActionListener(e -> activateGamePanel());

        // Allow game activation via any key press.
        welcome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                activateGamePanel();
            }
        });

        // Set the default button if the root pane is available.
        JRootPane rootPane = SwingUtilities.getRootPane(welcome);
        if (rootPane != null) {
            rootPane.setDefaultButton(start);
        } else {
            SwingUtilities.invokeLater(() -> {
                JRootPane rp = SwingUtilities.getRootPane(welcome);
                if (rp != null) {
                    rp.setDefaultButton(start);
                }
            });
        }

        // Configure layout and add components.
        welcome.setLayout(new BorderLayout());
        welcome.add(msg, BorderLayout.CENTER);
        welcome.add(start, BorderLayout.SOUTH);
    }

    /**
     * Transitions to the game panel and starts the game.
     */
    private void activateGamePanel() {
        uiService.showGamePanel();
        uiService.getEventPublisher().publish(new GameBeginEvent());
    }
}





