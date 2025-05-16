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

public class ConsoleWindow extends JFrame {
    private final Game game;
    private CardLayout cards = new CardLayout();
    private JPanel panelContainer = new JPanel(cards);
    private Map<Integer, JLabel> roomLabels;
    private JTextField inputField;
    private JTextArea outputArea; // Output area for game messages

    public ConsoleWindow(Game game) {
        super("Scrum Escape Game");
        this.game = game;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        initWelcomePanel();
        initMapPanel(player, rooms);

        add(panelContainer);
    }

    public void initWelcomePanel() {
        JPanel welcome = new JPanel(new BorderLayout());
        JLabel msg = new JLabel(
                "<html><h1>Welcome to Scrum Escape Game</h1>"
                        + "<p>Press START or ENTER to begin.</p></html>",
                SwingConstants.CENTER
        );
        JButton start = new JButton("START");
        start.addActionListener(e -> {
            cards.show(panelContainer, "map");
            Game game = new Game();
            game.beginGame();
        });
        getRootPane().setDefaultButton(start);

        welcome.add(msg, BorderLayout.CENTER);
        welcome.add(start, BorderLayout.SOUTH);
        panelContainer.add(welcome, "welcome");
    }

    private void initMapPanel(Player player, Map<Integer, Room> rooms) {
        JPanel mapCard = new JPanel(new BorderLayout(5, 5));

        // Ensure the panel can gain focus so that the keystroke bindings work.
        mapCard.setFocusable(true);

        // Create grid panel to display the room labels.
        // Use the number of rooms as the grid dimension.
        JPanel grid = new JPanel(new GridLayout(1, rooms.size(), 5, 5));

        // Create a mapping from room id to label.
        roomLabels = new HashMap<>();

        // Iterate over each room id and create a label.
        for (Integer id : rooms.keySet()) {
            JLabel lbl = new JLabel("", SwingConstants.CENTER);
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            roomLabels.put(id, lbl);
            grid.add(lbl);
        }

        updateMap(player, rooms);
        mapCard.add(grid, BorderLayout.NORTH);

        // Set up output area for game messages.
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mapCard.add(scrollPane, BorderLayout.CENTER);

        // Input field remains (optional) for text commands.
        inputField = new JTextField();
        inputField.addActionListener(e -> {
            String cmd = inputField.getText().trim().toLowerCase();
            Game.handleCommand(cmd);
            inputField.setText("");
            updateMap(player, rooms);
            // Return focus to the panel so that keystroke bindings remain active.
            mapCard.requestFocusInWindow();
        });
        mapCard.add(inputField, BorderLayout.SOUTH);

        // Set up keystroke bindings on the map panel.
        setupKeyBindings(mapCard);

        panelContainer.add(mapCard, "map");

        // Ensure the map panel is focused after it is displayed.
        SwingUtilities.invokeLater(() -> mapCard.requestFocusInWindow());
    }


    private void setupKeyBindings(JPanel panel) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

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
                updateMap(player, rooms);
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
                updateMap(player, rooms);
            }
        });
         */

        // Bind keystrokes for movement commands.
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveNorth");
        actionMap.put("moveNorth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("w");
                updateMap(player, rooms);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("A"), "moveWest");
        actionMap.put("moveWest", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("a");
                updateMap(player, rooms);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("S"), "moveSouth");
        actionMap.put("moveSouth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("s");
                updateMap(player, rooms);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("D"), "moveEast");
        actionMap.put("moveEast", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("d");
                updateMap(player, rooms);
            }
        });

        // Optionally, add other key bindings—for example, 'L' for "look".
        inputMap.put(KeyStroke.getKeyStroke("L"), "look");
        actionMap.put("look", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("look");
                updateMap(player, rooms);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("M"), "map");
        actionMap.put("map", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("map");
                updateMap(player, rooms);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("Q"), "answer");
        actionMap.put("answer", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.handleCommand("answer");
                updateMap(player, rooms);
            }
        });
    }

    public void updateMap(Player player, Map<Integer, Room> rooms) {
        for (Integer id : rooms.keySet()) {
            JLabel label = roomLabels.get(id);
            if (label != null) {
                // Display an "X" for the player's current room or the room number otherwise.
                label.setText(id.equals(player.getPosition()) ? "X" : id.toString());
            }
        }
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

