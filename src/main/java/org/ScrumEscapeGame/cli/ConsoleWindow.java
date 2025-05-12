package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static org.ScrumEscapeGame.cli.Game.player;
import static org.ScrumEscapeGame.cli.Game.rooms;

public class ConsoleWindow extends JFrame {
    private CardLayout cards = new CardLayout();
    private JPanel     panelContainer = new JPanel(cards);
    private JLabel[]   roomLabels;
    private JTextField inputField;
//DE CONSOLE WINDOW ZELF
    public ConsoleWindow() {
        super("Scrum Escape Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,200);
        setLocationRelativeTo(null);

        initWelcomePanel();
        initMapPanel(player, rooms);

        add(panelContainer);
    }
//WELKOM SCHERM
    public void initWelcomePanel() {
        JPanel welcome = new JPanel(new BorderLayout());
        JLabel msg = new JLabel(
                "<html><h1>Welcome to Scrum Escape Game</h1>"
                        + "<p>Press START or ENTER to begin.</p></html>",
                SwingConstants.CENTER
        );
        JButton start = new JButton("START");
        start.addActionListener(e -> cards.show(panelContainer,"map"));
        // bind ENTER op welcome scherm
        getRootPane().setDefaultButton(start);

        welcome.add(msg, BorderLayout.CENTER);
        welcome.add(start, BorderLayout.SOUTH);
        panelContainer.add(welcome, "welcome");
    }
// DIT IS DE MAP
    private void initMapPanel(Player player, Map<Integer,Room> rooms) {
        JPanel mapCard = new JPanel(new BorderLayout(5,5));
        JPanel grid    = new JPanel(new GridLayout(1, rooms.size(), 5,5));
        roomLabels     = new JLabel[rooms.size()+1];

        for (Integer id : rooms.keySet()) {
            JLabel lbl = new JLabel("", SwingConstants.CENTER);
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            roomLabels[id] = lbl;
            grid.add(lbl);
        }
        updateMap(player, rooms);
        mapCard.add(grid, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(e -> {
            String cmd = inputField.getText().trim().toLowerCase();
            Game.handleCommand(cmd);
            inputField.setText("");
            updateMap(player, rooms);
        });
        mapCard.add(inputField, BorderLayout.SOUTH);
        panelContainer.add(mapCard, "map");
    }
//DIT UPDATE DE MAP MET DE POSITIE VAN DE SPELER
    public void updateMap(Player player, Map<Integer,Room> rooms) {
        for (Integer id : rooms.keySet()) {
            roomLabels[id].setText(
                    id == player.getPosition() ? "X" : id.toString()
            );
        }
    }

    public void printMessage(String s) {
    }
}
