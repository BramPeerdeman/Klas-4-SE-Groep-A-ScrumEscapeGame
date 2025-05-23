package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAGame.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyBindSetup {

    public void setupGlobalKeyBindings(JPanel gamePanel) {
        // Ensure the panel is focusable and request focus for key events.
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        // Using the "WHEN_IN_FOCUSED_WINDOW" condition makes these bindings global.
        InputMap inputMap = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gamePanel.getActionMap();

    /*
        Hoe moet je een keystroke input toevoegen?
        Dat doe je op de volgende manier:
          1. maak je command aan met een klasse, kijk naar hoe de andere klasses zijn aangemaakt
          2. stop het in de commands map in de klasse game als volgt:
               commands.put("status", new StatusCommand(player));
          3. Stop de volgende code onderin deze methode:
        inputMap.put(KeyStroke.getKeyStroke("M"), "map");
        actionMap.put("map", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("map");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });
          4. Verander de keystroke "M" naar de keystroke die je wilt, en verander "map" naar de command die
             je wilt, bijvoorbeeld "status"
          5. dit is hoe het uitziet:
        inputMap.put(KeyStroke.getKeyStroke("P"), "status");
        actionMap.put("status", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("status");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });
         */

        // Example binding for "M" (refresh map) key:
        inputMap.put(KeyStroke.getKeyStroke("M"), "map");
        actionMap.put("map", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Refresh the map panel
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();

                // Optional message about current room
                int currentRoomId = Game.player.getPosition();
                String message = String.format("You are in room %d.", Game.rooms.get(currentRoomId).getDisplayOrder());
                Game.consoleWindow.printMessage(message);
            }
        });

        // Example binding for movement keys:
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveNorth");
        actionMap.put("moveNorth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("w");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("A"), "moveWest");
        actionMap.put("moveWest", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("a");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("S"), "moveSouth");
        actionMap.put("moveSouth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("s");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("D"), "moveEast");
        actionMap.put("moveEast", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("d");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        // Optionally, add other key bindings—for example, 'L' for "look".
        inputMap.put(KeyStroke.getKeyStroke("L"), "look");
        actionMap.put("look", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("look");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("M"), "map");
        actionMap.put("map", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("map");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("Q"), "answer");
        actionMap.put("answer", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("answer");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("F5"), "save");
        actionMap.put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("save");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("F9"), "load");
        actionMap.put("load", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("load");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });


        inputMap.put(KeyStroke.getKeyStroke("K"), "status");
        actionMap.put("status", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("status");
                Game.consoleWindow.getMapPanel().refreshCoordinates();
                Game.consoleWindow.getMapPanel().repaint();
            }
        });
    }
}
