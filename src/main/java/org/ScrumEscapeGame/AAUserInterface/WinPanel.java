package org.ScrumEscapeGame.AAUserInterface;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
    private JLabel winLabel;
    private JButton restartButton;

    public WinPanel(GameUIService uiService) {
        setLayout(new BorderLayout());
        winLabel = new JLabel("Congratulations, you won!", SwingConstants.CENTER);
        add(winLabel, BorderLayout.CENTER);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            // For example, call uiService.resetGame() or show the main game panel.
            uiService.handleGameReset("Restarting game");
        });
        add(restartButton, BorderLayout.SOUTH);
    }

    public void showMessage(String message) {
        winLabel.setText(message);
    }
}

