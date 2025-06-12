package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.InventoryClosedEvent;
import org.ScrumEscapeGame.AAEvents.InventoryOpenedEvent;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Commands.CommandManager;
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

    // New: Inventory panel reference and visibility flag.
    private InventoryPanel inventoryPanel;
    private boolean inventoryVisible = false;

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
        if(isInventoryVisible() && getInventoryPanel() != null) {
            getInventoryPanel().appendMessage(message);
        } else {
            console.printMessage(message);
        }
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
        inventoryVisible = false;
    }

    public void showQuestionPanel()
    {
        cards.show(panelContainer, "question");
        inventoryVisible = false;
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
     * @param commandLine the command string.
     */
    public void handle(String commandLine) {
        // Split the command line into the command key and everything else as arguments.
        String[] parts = commandLine.trim().split("\\s+", 2);
        String commandKey = parts[0];
        String args = (parts.length > 1) ? parts[1] : "";

        context.getCommandManager().handle(commandKey, context, console, args);
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
    /**
     * Toggles the inventory panel on or off.
     */
    public void toggleInventoryPanel() {
        if (inventoryVisible) {
            cards.show(panelContainer, "game");
            inventoryVisible = false;
            printMessage("Closing inventory...");
            // Publish event indicating the inventory has been closed.
            context.getEventPublisher().publish(new InventoryClosedEvent());
        } else {
            if (inventoryPanel == null) {
                inventoryPanel = new InventoryPanel(context, this);
                panelContainer.add(inventoryPanel, "inventory");
            }
            inventoryPanel.refresh();
            cards.show(panelContainer, "inventory");
            inventoryVisible = true;
            System.out.println("DEBUG: Inventory Opened");
            printMessage("Inventory opened.");
            // Publish event indicating the inventory is open.
            context.getEventPublisher().publish(new InventoryOpenedEvent());
        }
        inventoryPanel.refresh();
    }

    public boolean isInventoryVisible() {
        return inventoryVisible;
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public CommandManager getCommandManager() {
        return context.getCommandManager();
    }

    public void refreshInventory() {
        inventoryPanel.refresh();
    }

    //We'll probably use this method, to update the status on the sidebar.
    public void updateStatus(String statusMessage) {
        // For example, if you have a status label, update it:
        statusLabel.setText(statusMessage);
        // Otherwise, do nothing or log minimally.
    }

}


