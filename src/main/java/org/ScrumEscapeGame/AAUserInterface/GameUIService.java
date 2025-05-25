package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Provides common UI operations such as printing messages, reading input,
 * switching panels, and refreshing the map view.
 */
public class GameUIService implements DisplayService {
    private final GameContext context;
    private final ConsoleWindow console;
    private CardLayout cards;
    private JPanel panelContainer;
    // (Optional) a map of room labels for dynamic updates.
    private Map<Integer, JLabel> roomLabels;
    private MapPanel mapPanel;
    private JTextArea outputArea;
    private JTextField inputField;
    private JLabel statusLabel; // Displays player status.

    /**
     * Constructs the UI service necessary for handling UI behavior.
     *
     * @param context       the game context containing game state.
     * @param console       the ConsoleWindow instance.
     * @param cards         the CardLayout for panel switching.
     * @param panelContainer the container holding the panels.
     * @param mapPanel      the shared MapPanel.
     * @param outputArea    the shared output text area.
     * @param inputField    the shared input text field.
     * @param statusLabel   the status label for displaying player status.
     */
    public GameUIService(GameContext context, ConsoleWindow console, CardLayout cards,
                         JPanel panelContainer, MapPanel mapPanel,
                         JTextArea outputArea, JTextField inputField, JLabel statusLabel) {
        this.context = context;
        this.console = console;
        this.cards = cards;
        this.panelContainer = panelContainer;
        this.mapPanel = mapPanel;
        this.outputArea = outputArea;
        this.inputField = inputField;
        this.statusLabel = statusLabel;
    }

    @Override
    public void printMessage(String message) {
        console.printMessage(message);
    }

    @Override
    public String readLine(String prompt) {
        return console.readLine(prompt);
    }

    /**
     * Switches the visible panel to the game panel.
     */
    public void showGamePanel() {
        cards.show(panelContainer, "game");
    }

    /**
     * Returns the room by its identifier from the context.
     */
    public Room getRoom(int id) {
        return context.getRoomManager().getRoom(id);
    }

    /**
     * Returns the map of rooms.
     */
    public Map<Integer, Room> getRooms() {
        return context.getRoomManager().getRooms();
    }

    /**
     * Returns the current player.
     */
    public Player getPlayer() {
        return context.getPlayer();
    }

    /**
     * Refreshes the map view by updating the status label and redrawing the MapPanel.
     */
    public void refreshMapView() {
        statusLabel.setText("Player Status: " + context.getPlayer().getStatus());
        mapPanel.refreshCoordinates();
        mapPanel.repaint();
    }

    /**
     * Handles a command received from the user.
     *
     * @param command the command string.
     */
    public void handle(String command) {
        context.getCommandManager().handle(command, context, console);
    }

    /**
     * Returns the event publisher for game events.
     */
    public EventPublisher<GameEvent> getEventPublisher() {
        return context.getEventPublisher();
    }

    /**
     * Returns the MapPanel instance.
     */
    public MapPanel getMapPanel() {
        return mapPanel;
    }
}


