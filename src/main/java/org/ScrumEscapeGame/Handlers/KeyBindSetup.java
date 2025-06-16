package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * KeyBindSetup is responsible for configuring global key bindings on a given game panel.
 * It uses Swing’s InputMap and ActionMap to define actions for specific keystrokes.
 * These key bindings are set to use the WHEN_IN_FOCUSED_WINDOW condition,
 * meaning they will be active even if the panel itself does not have focus.
 */
public class KeyBindSetup {
    private final GameUIService uiService;

    /**
     * Constructs a KeyBindSetup instance using the provided GameUIService,
     * which is used to delegate command handling and UI refreshing.
     *
     * @param uiService the service offering common UI operations.
     */
    public KeyBindSetup(GameUIService uiService) {
        this.uiService = uiService;
    }

    /**
     * Configures global key bindings on the given gamePanel.
     *
     * Example:
     * - Pressing "M" refreshes the map, prints a message on the current room,
     *   and calls the appropriate UIService methods.
     * - Movement keys "W", "A", "S", "D" are registered to handle directional commands.
     * - Other keys such as "L", "Q", "F5", "F9", and "K" are also set up.
     *
     * Any new key bindings can be added here using:
     *   inputMap.put(KeyStroke.getKeyStroke("KEY"), "commandName");
     *   actionMap.put("commandName", new AbstractAction() { ... });
     *
     * @param gamePanel the panel on which key bindings are to be set.
     */
    public void setupGlobalKeyBindings(JPanel gamePanel) {
        // Ensure that the game panel can receive key events.
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        // Use WHEN_IN_FOCUSED_WINDOW to allow keybindings even when other components have focus.
        InputMap inputMap = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gamePanel.getActionMap();

        // Example key binding: "M" to refresh map and print the current room's display order.
        inputMap.put(KeyStroke.getKeyStroke("M"), "map");
        actionMap.put("map", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("map");
                uiService.refreshMapView();
            }
        });

        // Movement bindings: 'W' for north, 'A' for west, 'S' for south, 'D' for east.
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveNorth");
        actionMap.put("moveNorth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("w");
                uiService.refreshMapView();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("A"), "moveWest");
        actionMap.put("moveWest", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("a");
                uiService.refreshMapView();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("S"), "moveSouth");
        actionMap.put("moveSouth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("s");
                uiService.refreshMapView();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("D"), "moveEast");
        actionMap.put("moveEast", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("d");
                uiService.refreshMapView();
            }
        });

        // Additional binding: "L" for look command.
        inputMap.put(KeyStroke.getKeyStroke("L"), "look");
        actionMap.put("look", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("look");
                uiService.refreshMapView();
            }
        });

        // Binding: "Q" to trigger the answer command (for questions in rooms).
//        inputMap.put(KeyStroke.getKeyStroke("Q"), "answer");
//        actionMap.put("answer", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                uiService.handle("answer");
//                uiService.refreshMapView();
//            }
//        });

        // Bind F5 to save the game, and F9 to load.
        inputMap.put(KeyStroke.getKeyStroke("F5"), "save");
        actionMap.put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("save");
                uiService.refreshMapView();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("F9"), "load");
        actionMap.put("load", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("load");
                uiService.refreshMapView();
            }
        });

        // Bind "K" to check the current status.
        inputMap.put(KeyStroke.getKeyStroke("K"), "status");
        actionMap.put("status", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("status");
                uiService.refreshMapView();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("R"), "hint");
        actionMap.put("hint", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.handle("hint");
                uiService.refreshMapView();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("I"), "toggleInventory");
        actionMap.put("toggleInventory", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.toggleInventoryPanel();
                uiService.refreshMapView();
            }
        });


    }
}

