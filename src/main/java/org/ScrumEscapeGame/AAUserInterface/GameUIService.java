package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Commands.CommandManager;
import org.ScrumEscapeGame.GameObjects.*;
import org.ScrumEscapeGame.Monster.MonsterManager;
import org.ScrumEscapeGame.Monster.StatueMonster;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Rooms.BossRoom;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

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
    private boolean gameOver = false;

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
        // In GameUIService constructor:
        this.inventoryPanel = new InventoryPanel(context, this, false);
        panelContainer.add(inventoryPanel, "inventory");

        // For the question panel, create an InventoryPanel in question mode (only player inventory).
        InventoryPanel questionInventoryPanel = new InventoryPanel(context, this, true);
        // After creating the question panel, set its embedded inventory panel’s callback to be the question panel.
        this.questionPanel = new QuestionPanel(questionInventoryPanel, this, context);
        panelContainer.add(questionPanel, "question");


    }

    @Override
    public void printMessage(String message) {
        // If the question panel is open, route all messages to its embedded inventory panel.
        if (questionVisible && questionPanel != null) {
            questionPanel.getEmbeddedInventoryPanel().appendMessage(message);
        } else if (isInventoryVisible() && getInventoryPanel() != null) {
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
        MonsterManager.getInstance().tick(context.getPlayer(), context.getEventPublisher());

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
        // Get current room.
        int pos = context.getPlayer().getPosition();
        Room currentRoom = context.getRoomManager().getRooms().get(pos);

        // Special case: Current room is a BossRoom.
        if (currentRoom instanceof BossRoom bossRoom) {
            if (gameOver) {
                printMessage("Game is over.");
                return;
            }

            // Check if the boss challenge is already complete.
            if (bossRoom.getQuestionsAnsweredCount() == 3) {
                questionPanel.setQuestionText("You have already conquered the boss challenge.");
                questionPanel.setSubmitAction(e -> {
                    showGamePanel();
                    questionVisible = false;
                });
            } else {
                // Load the current boss question based on how many have been answered.
                loadBossQuestion(bossRoom);
            }
            cards.show(panelContainer, "question");
            questionVisible = true;
            printMessage("Boss challenge panel opened.");
            return; // Exit here since boss room processing is complete.
        }

        // Otherwise: handle regular RoomWithQuestion challenge.
        if (!(currentRoom instanceof RoomWithQuestion roomWithQuestion) ||
                roomWithQuestion.getQuestionWithHints() == null) {
            printMessage("There is no challenge in this room.");
            return;
        }

        // If the panel is already open, warn and do nothing.
        if (questionVisible) {
            printMessage("Puzzle screen is already open.");
            return;
        }

        // Reset the attempt count.
        roomWithQuestion.attemptCount = 0;

        if (context.getPlayer().isRoomSolved(roomWithQuestion.getId())) {
            questionPanel.setQuestionText("You have already solved this puzzle.");
            questionPanel.setSubmitAction(e -> {
                showGamePanel();
                questionVisible = false;
            });
        } else {
            // Load the room challenge question.
            QuestionWithHints qw = roomWithQuestion.getQuestionWithHints();
            org.ScrumEscapeGame.GameObjects.Question question = qw.getQuestion();
            questionPanel.loadQuestion(
                    question.getPrompt(),
                    question.getChoices().toArray(new String[0]),
                    question.getCorrectAnswer()
            );
            // Set up the submit action for the room challenge.
            questionPanel.setSubmitAction(e -> {
                if (gameOver) {
                    return;
                }
                String providedAnswer = questionPanel.getSelectedAnswer();
                MultipleChoiceStrategy mcs = new MultipleChoiceStrategy();
                boolean correct = mcs.evaluateAnswer(providedAnswer, question, this);

                if (correct) {
                    context.getPlayer().addSolvedRoom(roomWithQuestion.getId());
                    roomWithQuestion.setChallengeCleared(true);
                    if (roomWithQuestion.hasActiveMonster()) {
                        roomWithQuestion.getActiveMonster().die();
                        roomWithQuestion.setActiveMonster(null);
                    }
                    getEventPublisher().publish(new DoorUnlockedEvent(roomWithQuestion.getAssociatedDoor()));
                    printMessage("Puzzle solved and door unlocked!");
                    roomWithQuestion.attemptCount = 0;
                    showGamePanel();
                    questionVisible = false;
                } else {
                    roomWithQuestion.attemptCount++;
                    if (roomWithQuestion.attemptCount == 1) {
                        if (roomWithQuestion.hasHelper()) {
                            String randomHint = roomWithQuestion.getHintProviderSelector()
                                    .selectHintProvider(qw.getHintProviders())
                                    .getHint();
                            getEventPublisher().publish(new NotificationEvent("Here's a hint: " + randomHint));
                        }
                        printMessage("Incorrect answer. Please try again or use a lifeline.");
                        // Keep panel open for reattempt.
                    } else {
                        getEventPublisher().publish(new NotificationEvent("Incorrect again! A monster awakens..."));
                        if (roomWithQuestion.hasStatue()) {
                            try {
                                StatueMonster monster = new StatueMonster("Backlog Beast",
                                        "The statue awakens with a menacing glare.",
                                        getEventPublisher(), null, 5);
                                monster.spawn();
                                MonsterManager.getInstance().registerActiveMonster(monster);
                                roomWithQuestion.setActiveMonster(monster);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        showGamePanel();
                        questionVisible = false;
                    }
                }
                // Trigger a tick after processing the answer.
                MonsterManager.getInstance().tick(this.getPlayer(), context.getEventPublisher());
                if (this.getPlayer().getHitPoints() <= 0) {
                    MonsterManager.getInstance().clearActiveMonsters();
                    this.handleGameReset("You died while attempting the challenge!");
                }
            });
        }

        cards.show(panelContainer, "question");
        questionVisible = true;
        printMessage("Puzzle screen opened.");
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

    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }

    public void handleGameReset(String reason) {
        // Close any open panels—here, ensure the question panel is closed:
        this.showGamePanel();
        this.questionVisible = false;
        // Display a reset message.
        printMessage(reason);
        // Clear active monsters so no further damage is applied.
        MonsterManager.getInstance().clearActiveMonsters();
        // Optionally: Transition to a reset screen or reinitialize game state.
        // For example:
        // gameReset.reset();   // if you have a GameReset instance.
        context.getEventPublisher().publish(new GameResetEvent(reason));
    }

    public void removeQuestionPanel() {
        questionPanel.clear();
        this.showGamePanel();
        this.questionVisible = false;
    }

    private void loadBossQuestion(BossRoom bossRoom) {
        // Get the current boss question based on how many have been answered.
        int currentIndex = bossRoom.getQuestionsAnsweredCount();
        Question currentQ = bossRoom.getQuestions().get(currentIndex);

        // Load the question into the panel.
        questionPanel.loadQuestion(
                currentQ.getPrompt(),
                currentQ.getChoices().toArray(new String[0]),
                currentQ.getCorrectAnswer()
        );

        // Bind a fresh submit action that captures the current question.
        questionPanel.setSubmitAction(e -> {
            if (gameOver) {
                return;
            }
            String providedAnswer = questionPanel.getSelectedAnswer();
            MultipleChoiceStrategy mcs = new MultipleChoiceStrategy();
            boolean correct = mcs.evaluateAnswer(providedAnswer, currentQ, this);
            if (!correct) {
                // Wrong answer triggers reset.
                getEventPublisher().publish(new GameResetEvent("Wrong answer in boss room, game resetting...."));
                showGamePanel();
                questionVisible = false;
            } else {
                bossRoom.incrementQuestionsAnsweredCount();
                printMessage("Boss question answered correctly.");
                if (bossRoom.getQuestionsAnsweredCount() == 3) {
                    // Boss challenge complete!
                    getEventPublisher().publish(new NotificationEvent(
                            "Congratulations, you answered all boss questions correctly, you won!"
                    ));
                    showGamePanel();
                    questionVisible = false;
                    return; // Stop further processing.
                } else {
                    // For a correct answer, load the next boss question.
                    loadBossQuestion(bossRoom);
                    return; // Ensure we do not continue executing further code here.
                }
            }
            // After processing, trigger a monster tick.
            MonsterManager.getInstance().tick(this.getPlayer(), context.getEventPublisher());
            if (this.getPlayer().getHitPoints() <= 0) {
                MonsterManager.getInstance().clearActiveMonsters();
                this.handleGameReset("You died while attempting the boss challenge!");
            }
        });
    }

}


