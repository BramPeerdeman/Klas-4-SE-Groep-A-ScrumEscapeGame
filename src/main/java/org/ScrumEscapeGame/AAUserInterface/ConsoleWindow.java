package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Handlers.KeyBindSetup;

import javax.swing.*;
import java.awt.*;

/*
Weet je niet waar je aan moet werken?
Je kan dan mee helpen door onze code te optimaliseren.
Bijvoorbeeld we kunnen proberen zoveel mogelijke klassen aan te maken die maar 1 ding doen in plaats van
dat alles in de ConsoleWindow en Game klasse zitten. Denk aan de beginGame() en resetGame() methodes, die
kunnen dus hun eigen klassen hebben.
 */

/**
 * The main game window. It organizes the UI using a CardLayout,
 * creating and sharing key components (e.g., the MapPanel, output area,
 * input field, and status label) and wiring them into the GameUIService.
 */
public class ConsoleWindow extends JFrame {
    // Main game context (rooms, player, command manager, etc.)
    private final GameContext context;

    // For switching between panels (like a welcome panel and a game panel)
    private final CardLayout cards;
    private final JPanel panelContainer;

    // Shared UI components
    private final MapPanel mapPanel;
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final JLabel statusLabel;

    // Service that provides methods to update the UI, print messages, etc.
    private final GameUIService uiService;

    /**
     * Constructs the main game window.
     *
     * @param context the game context (state, rooms, player, etc.)
     */
    public ConsoleWindow(GameContext context) {
        super("Scrum Escape Game");
        this.context = context;

        // Initialize the CardLayout and container for panels.
        this.cards = new CardLayout();
        this.panelContainer = new JPanel(cards);

        // Create shared UI components.
        // The MapPanel is created once using the RoomManager's current room map and player reference.
        this.mapPanel = new MapPanel(context.getRoomManager().getRooms(), context.getPlayer());
        this.outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        this.inputField = new JTextField();
        this.statusLabel = new JLabel("Player Status: " + context.getPlayer().getStatus());

        // Create the UI service by injecting the required dependencies.
        this.uiService = new GameUIService(
                context,
                this,
                cards,
                panelContainer,
                mapPanel,
                outputArea,
                inputField,
                statusLabel
        );

        // Build and set up the panels (e.g. welcome and game panels) and add them to the container.
        initPanels();

        // Add the panel container to the frame.
        add(panelContainer);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the panels used in the application.
     * Creates and adds a welcome panel and a game panel to the CardLayout container.
     */
    private void initPanels() {
        // Initialize the welcome panel.
        WelcomePanel welcomePanel = new WelcomePanel(uiService);
        JPanel welcomeContainer = new JPanel(new BorderLayout());
        welcomePanel.initWelcomePanel(welcomeContainer);
        panelContainer.add(welcomeContainer, "welcome");

        // Initialize the game panel.
        KeyBindSetup keyBindSetup = new KeyBindSetup(uiService);
        GamePanel gamePanel = new GamePanel(uiService, keyBindSetup, outputArea,
                uiService.getRooms(), uiService.getPlayer());
        panelContainer.add(gamePanel, "game");

        // Show the welcome panel initially.
        cards.show(panelContainer, "welcome");
    }

    /**
     * Refreshes parts of the UI based on the current game state.
     * Called when the game state changes.
     */
    public void refreshUI() {
        statusLabel.setText("Player Status: " + context.getPlayer().getStatus());
        mapPanel.refreshCoordinates();
    }

    /**
     * Prints a message to the shared output area.
     *
     * @param message the message to print
     */
    public void printMessage(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    /**
     * Blocks for user input by showing a dialog prompt.
     *
     * @param prompt the prompt message to show
     * @return the user input as a String
     */
    public String readLine(String prompt) {
        return JOptionPane.showInputDialog(this, prompt);
    }

    public GameUIService getUiService() {
        return uiService;
    }

    /**
     * Returns the shared MapPanel.
     *
     * @return the MapPanel instance
     */
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public void clearMessages() {
        outputArea.setText("");
    }
}



