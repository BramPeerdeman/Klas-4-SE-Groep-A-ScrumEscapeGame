package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static org.ScrumEscapeGame.cli.Game.player;
import static org.ScrumEscapeGame.cli.Game.rooms;

/*
Weet je niet waar je aan moet werken?
Je kan dan mee helpen door onze code te optimaliseren.
Bijvoorbeeld we kunnen proberen zoveel mogelijke klassen aan te maken die maar 1 ding doen in plaats van
dat alles in de ConsoleWindow en Game klasse zitten. Denk aan de beginGame() en resetGame() methodes, die
kunnen dus hun eigen klassen hebben.
 */

public class ConsoleWindow extends JFrame {
    private final Game game;
    private CardLayout cards = new CardLayout();
    private JPanel panelContainer = new JPanel(cards);
    private Map<Integer, JLabel> roomLabels;
    private MapPanel mapPanel;
    private JTextArea outputArea;
    private JTextField inputField;
    private JLabel statusLabel; // Shows player status.

    public ConsoleWindow(Game game) {
        super("Scrum Escape Game");
        this.game = game;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);  // Increased size for a richer UI
        setLocationRelativeTo(null);

        initWelcomePanel();
        initGamePanel();

        add(panelContainer);
    }

    // The welcome panel with a start message.
    private void initWelcomePanel() {
        JPanel welcome = new JPanel(new BorderLayout());
        JLabel msg = new JLabel(
                "<html><h1>Welcome to Scrum Escape Game</h1>"
                        + "<p>Press START or ENTER to begin.</p></html>",
                SwingConstants.CENTER
        );
        JButton start = new JButton("START");
        start.addActionListener(e -> {
            cards.show(panelContainer, "game");
            game.beginGame();
        });
        // Set the default button so that ENTER starts the game.
        getRootPane().setDefaultButton(start);

        welcome.add(msg, BorderLayout.CENTER);
        welcome.add(start, BorderLayout.SOUTH);
        panelContainer.add(welcome, "welcome");
    }

    // The game UI panel, organized into left and main (right/center) sections.
    private void initGamePanel() {
    // The gamePanel holds all game-related UI components.
    JPanel gamePanel = new JPanel(new BorderLayout());

    // --- Left Side Panel: Status and Map ---
    JPanel leftPanel = new JPanel(new BorderLayout());

    // Top (Status Panel)
    JPanel statusPanel = new JPanel();
    statusPanel.setPreferredSize(new Dimension(250, 150));
    statusLabel = new JLabel("Player Status: " + Game.player.getStatus());
    statusPanel.add(statusLabel);
    leftPanel.add(statusPanel, BorderLayout.NORTH);

    // Bottom (Map Panel)
    // Create your MapPanel using the rooms and player.
    mapPanel = new MapPanel(Game.rooms, Game.player);
    leftPanel.add(mapPanel, BorderLayout.CENTER);

    // --- Right/Center Panel: Game Output and Input ---
    JPanel mainPanel = new JPanel(new BorderLayout());
    outputArea = new JTextArea(15, 40);
    outputArea.setEditable(false);
    outputArea.setLineWrap(true);
    outputArea.setWrapStyleWord(true);
    JScrollPane outputScroll = new JScrollPane(outputArea);
    mainPanel.add(outputScroll, BorderLayout.CENTER);

    // Input field for text commands.
    inputField = new JTextField();
    inputField.addActionListener(e -> {
        String cmd = inputField.getText().trim().toLowerCase();
        Game.handleCommand(cmd);
        inputField.setText("");
    });
    mainPanel.add(inputField, BorderLayout.SOUTH);

    // Assemble the overall game UI.
    JPanel overallGamePanel = new JPanel(new BorderLayout());
    overallGamePanel.add(leftPanel, BorderLayout.WEST);
    overallGamePanel.add(mainPanel, BorderLayout.CENTER);

    // Add the game UI to the card layout.
    panelContainer.add(overallGamePanel, "game");

    // Set up global key bindings on the overall game panel.
    setupGlobalKeyBindings(overallGamePanel);

    // Optionally request focus on the overall panel
    SwingUtilities.invokeLater(() -> overallGamePanel.requestFocusInWindow());
}


    // Call this method after the room map has been built to update the status and map.
    public void refreshUI() {
        statusLabel.setText("Player Status: " + Game.player.getStatus());
        // Recalculate room positions in the map.
        mapPanel.refreshCoordinates();
    }



    // Expose the MapPanel so that commands can refresh it if needed.
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    // Optionally, a method to switch to the welcome screen.
    public void showWelcomePanel() {
        cards.show(panelContainer, "welcome");
    }


    // Add this at the end of initGamePanel() in ConsoleWindow (or call it from there)
private void setupGlobalKeyBindings(JPanel gamePanel) {
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


    // Print a message to the output area.
    public void printMessage(String s) {
        outputArea.append(s + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    // Basic synchronous input (blocking) for question answering.
    public String readLine(String prompt) {
        return JOptionPane.showInputDialog(this, prompt);
    }
}

