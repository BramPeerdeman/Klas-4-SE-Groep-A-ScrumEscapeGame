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

public class ConsoleWindow extends JFrame {
    // Main game reference.
    private final GameContext context;

    // UI Components for managing panels.
    private final CardLayout cards;
    private final JPanel panelContainer;

    // Shared UI components.
    private final MapPanel mapPanel;
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final JLabel statusLabel;

    // The GameUIService that offers common UI operations.
    private final GameUIService uiService;

    public ConsoleWindow(GameContext context) {
        super("Scrum Escape Game");
        this.context = context;

        // Initialize layout and shared UI components.
        this.cards = new CardLayout();
        this.panelContainer = new JPanel(cards);

        // Initialize the Swing components that will be shared.
        this.mapPanel = new MapPanel(context.getRoomManager().getRooms(),
                context.getPlayer());
        this.outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        this.inputField = new JTextField();
        this.statusLabel = new JLabel("Player Status: " + context.getPlayer().getStatus());

        // Create the UI service by injecting the required dependencies.
        this.uiService = new GameUIService(
                context,
                this, // Pass ConsoleWindow as it implements behaviors like printMessage.
                cards,
                panelContainer,
                mapPanel,
                outputArea,
                inputField,
                statusLabel
        );

        // Build and set up the panels.
        initPanels();

        // Set up ConsoleWindow frame properties.
        add(panelContainer);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    /**
     * Creates the panels (welcome panel, game panel, etc.) and adds them to the card layout.
     */
    private void initPanels() {
        // Initialize WelcomePanel with the GameUIService.
        WelcomePanel welcomePanel = new WelcomePanel(uiService);
        JPanel welcomeContainer = new JPanel(new BorderLayout());
        welcomePanel.initWelcomePanel(welcomeContainer);
        panelContainer.add(welcomeContainer, "welcome");

        // Initialize GamePanel with the GameUIService.
        // For example, if your GamePanel now accepts a GameUIService and key binding setup.
        KeyBindSetup keyBindSetup = new KeyBindSetup();
        GamePanel gamePanel = new GamePanel(uiService, keyBindSetup);
        panelContainer.add(gamePanel, "game");

        // Optionally, show the welcome panel by default.
        cards.show(panelContainer, "welcome");
    }

    /**
     * Allows the UI service to update the status and map.
     * This method is called whenever the game state changes.
     */
    public void refreshUI() {
        statusLabel.setText("Player Status: " + context.getPlayer().getStatus());
        mapPanel.refreshCoordinates();
    }

    /**
     * Serves as a helper method for the GameUIService to print messages.
     */
    public void printMessage(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    /**
     * Provides blocking input to answer synchronous questions.
     */
    public String readLine(String prompt) {
        return JOptionPane.showInputDialog(this, prompt);
    }
}


