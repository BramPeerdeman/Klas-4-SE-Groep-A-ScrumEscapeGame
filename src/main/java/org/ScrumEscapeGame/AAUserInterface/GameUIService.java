package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GameUIService {
    private final GameContext context;
    private final ConsoleWindow console;
    private CardLayout cards = new CardLayout();
    private JPanel panelContainer = new JPanel(cards);
    private Map<Integer, JLabel> roomLabels;
    private MapPanel mapPanel;
    private JTextArea outputArea;
    private JTextField inputField;
    private JLabel statusLabel; // Shows player status.

    public GameUIService(GameContext context, ConsoleWindow console, CardLayout cards, JPanel panelContainer, MapPanel mapPanel
            , JTextArea outputArea, JTextField inputField, JLabel statusLabel) {
        this.context = context;
        this.console = console;
        this.cards = cards;
        this.panelContainer = panelContainer;
        this.mapPanel = mapPanel;
        this.outputArea = outputArea;
        this.inputField = inputField;
        this.statusLabel = statusLabel;
    }

    public void printMessage(String message) {
        console.printMessage(message);
    }

    public void showGamePanel() {
        // Switch to the game panel.
        cards.show(panelContainer, "game");
    }

    public Room getRoom(int id) {
        return context.getRoomManager().getRoom(id);
    }

    public Map<Integer, Room> getRooms() {
        return context.getRoomManager().getRooms();
    }

    public Player getPlayer() {
        return context.getPlayer();
    }

    public void refreshMapView() {
        mapPanel.refreshCoordinates();
        mapPanel.repaint();
    }



    // Additional methods to control UI behavior.
}

