package org.ScrumEscapeGame.AAUserInterface;


import org.ScrumEscapeGame.AAEvents.QuestionOpenedEvent;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Handlers.KeyBindSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * The GamePanel is the main in-game UI. It composes the left panel (the map plus a status area)
 * and the right panel (containing output messages and a command input). It also delegates the
 * map operations to the shared MapPanel instance.
 */
public class GamePanel extends JPanel {
    private final GameUIService uiService;
    private final KeyBindSetup keyBindSetup;
    // Do not create a new MapPanel; reuse the one passed in from UIService.
    private final MapPanel mapPanel;
    private JTextField inputField;
    private final JTextArea outputArea;  // Shared output area.

    /**
     * Constructs the GamePanel.
     *
     * @param uiService        The UI service providing operations and shared components.
     * @param keyBindSetup     The key binding configuration.
     * @param sharedOutputArea The shared output text area.
     * @param rooms            The map of rooms.
     * @param player           The current player.
     */
    public GamePanel(GameUIService uiService, KeyBindSetup keyBindSetup,
                     JTextArea sharedOutputArea, Map<Integer, Room> rooms, Player player) {
        this.uiService = uiService;
        this.keyBindSetup = keyBindSetup;
        this.outputArea = sharedOutputArea;
        // Reuse the shared MapPanel managed by the UI service.
        this.mapPanel = uiService.getMapPanel();
        initGamePanel();
    }

    /**
     * Initializes the UI layout of the GamePanel by composing the left and right panels.
     */
    private void initGamePanel() {
        setLayout(new BorderLayout());

        // ---------------------- Left Panel ----------------------
        // Left panel will contain the map and a top status area.
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 400));

        // Status panel (optional).
        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(400, 50)); // fixed height.
        JLabel statusLabel = uiService.getStatusLabel();
        // Optionally update its text immediately:
        statusLabel.setText("Player Health: " + uiService.getPlayer().getHitPoints() + "/5");
        statusPanel.add(statusLabel);

        leftPanel.add(statusPanel, BorderLayout.NORTH);

        // Add the shared MapPanel.
        leftPanel.add(mapPanel, BorderLayout.CENTER);

        // ---------------------- Right Panel ----------------------
        // Right panel will contain the output area and command input field.
        JPanel rightPanel = new JPanel(new BorderLayout());
        JScrollPane outputScroll = new JScrollPane(outputArea);
        rightPanel.add(outputScroll, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(e -> {
            String cmd = inputField.getText().trim().toLowerCase();
            uiService.handle(cmd);
            inputField.setText("");
        });
        rightPanel.add(inputField, BorderLayout.SOUTH);

        // ---------------------- Assemble Overall Layout ----------------------
        // Use a JSplitPane to separate the left (map) and right (output/input) regions.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"), "toggleQuestion");
        this.getActionMap().put("toggleQuestion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.toggleQuestionPanel();
            }
        });

        // Set up global key bindings.
        keyBindSetup.setupGlobalKeyBindings(this);
        // Request focus for key events.
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }

    /**
     * Allows external callers to refresh the MapPanel.
     */
    public void refreshMapPanel() {
        mapPanel.refreshCoordinates();
    }
}





