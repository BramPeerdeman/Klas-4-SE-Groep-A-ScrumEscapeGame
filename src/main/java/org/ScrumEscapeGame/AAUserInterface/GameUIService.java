package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Commands.CommandManager;
import org.ScrumEscapeGame.GameObjects.*;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;

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
    private final CardLayout cards;
    private final JPanel panelContainer;
    private final MapPanel mapPanel;
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final JLabel statusLabel;

    private final InventoryPanel inventoryPanel;
    private final QuestionPanel questionPanel;
    private TerminalPanel terminalPanel;

    private boolean inventoryVisible = false;
    private boolean questionVisible = false;
    private boolean terminalVisible = false;

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

        // Initialize both panels immediately so they are never null
        this.inventoryPanel = new InventoryPanel(context, this);
        panelContainer.add(inventoryPanel, "inventory");

        this.questionPanel = new QuestionPanel(inventoryPanel);
        panelContainer.add(questionPanel, "question");
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
            context.getEventPublisher().publish(new InventoryClosedEvent());
        } else {
            inventoryPanel.refresh();
            cards.show(panelContainer, "inventory");
            inventoryVisible = true;
            printMessage("Inventory opened.");
            context.getEventPublisher().publish(new InventoryOpenedEvent());
        }
    }


    public void toggleQuestionPanel() {
        if (questionVisible) {
            showGamePanel();
            printMessage("Closing puzzle screen...");
            questionVisible = false;
        } else {
            int pos = context.getPlayer().getPosition();
            Room currentRoom = context.getRoomManager().getRooms().get(pos);
            int roomId = currentRoom.getId();

            if (context.getPlayer().isRoomSolved(roomId)) {
                questionPanel.setQuestionText("You have already solved this puzzle.");
                questionPanel.setSubmitAction(e -> {
                    // Close immediately
                    showGamePanel();
                    questionVisible = false;
                });
            } else if (currentRoom instanceof RoomWithQuestion roomWithQuestion) {
                // Load question normally
                QuestionWithHints qw = roomWithQuestion.getQuestionWithHints();
                org.ScrumEscapeGame.GameObjects.Question question = qw.getQuestion();

                questionPanel.loadQuestion(
                        question.getPrompt(),
                        question.getChoices().toArray(new String[0]),
                        question.getCorrectAnswer()
                );

                // Set submit action to delegate question handling to triggerQuestion
                questionPanel.setSubmitAction(e -> {
                    // Call the triggerQuestion logic here
                    roomWithQuestion.triggerQuestion(
                            context.getPlayer(),
                            context.getEventPublisher(),
                            this // GameUIService itself for display callbacks
                    );

                    // After question logic, close question panel
                    showGamePanel();
                    questionVisible = false;
                });
            } else {
                questionPanel.setQuestionText("No question in this room.");
                questionPanel.setSubmitAction(e -> {
                    showGamePanel();
                    questionVisible = false;
                });
            }

            cards.show(panelContainer, "question");
            questionVisible = true;
            printMessage("Puzzle screen opened.");
        }
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

    /**
     * Toggles the terminal (assistant) panel on or off.
     */
    public void toggleTerminalPanel() {
        if (terminalVisible) {
            // Hide the terminal panel, revert to the game panel.
            cards.show(panelContainer, "game");
            terminalVisible = false;
            // Publish event indicating the assistant panel has been closed.
            context.getEventPublisher().publish(new TerminalClosedEvent());
        } else {
            // Create the terminal panel if it does not exist.
            if (terminalPanel == null) {
                terminalPanel = new TerminalPanel(context, this);
                panelContainer.add(terminalPanel, "terminal");
            }
            // Activate the assistant (update the hint, tutorial, and motivational message).
            terminalPanel.activateAssistant();
            // Show the terminal panel.
            cards.show(panelContainer, "terminal");
            terminalVisible = true;
            System.out.println("DEBUG: Terminal Panel Opened");
            // Publish event indicating the assistant panel is open.
            context.getEventPublisher().publish(new TerminalOpenedEvent());
        }
    }

}


