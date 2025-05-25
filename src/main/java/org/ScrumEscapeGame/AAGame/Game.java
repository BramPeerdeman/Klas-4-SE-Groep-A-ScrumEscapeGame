package org.ScrumEscapeGame.AAGame;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.AAUserInterface.ConsoleWindow;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.Commands.CommandManager;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

/**
 * The main game entry point.
 * This class manages command execution, UI control, and game lifecycle events.
 */
public class Game {
    private final CommandManager commandManager;
    private final RoomManager roomManager;
    private final Player player;
    private final EventPublisher<GameEvent> publisher;
    private final GameContext gameContext;
    private final ConsoleWindow consoleWindow;
    private final GameCycleManager cycleManager;
    private final GameUIService uiService;  // Implements DisplayService and handles UI events.

    /**
     * Constructs the game instance using provided dependencies.
     * All objects are passed via constructor to facilitate clean dependency injection.
     *
     * @param gameContext    The game context containing player, rooms, and event handling.
     * @param consoleWindow  The main console window for UI interactions.
     * @param cycleManager   The game cycle manager responsible for starting and resetting the game.
     */
    public Game(GameContext gameContext, ConsoleWindow consoleWindow, GameCycleManager cycleManager) {
        this.gameContext = gameContext;
        this.consoleWindow = consoleWindow;
        this.cycleManager = cycleManager;

        // Extract dependencies from the game context.
        this.player = gameContext.getPlayer();
        this.roomManager = gameContext.getRoomManager();
        this.publisher = gameContext.getEventPublisher();
        this.commandManager = gameContext.getCommandManager();
        this.uiService = consoleWindow.getUiService();

        // Debugging - Print out rooms at startup.
        for (Room room : roomManager.getRooms().values()) {
            System.out.printf("Room ID: %d, Display Order: %d%n", room.getId(), room.getDisplayOrder());
        }
        System.out.println("Game initialized.");
    }

    /**
     * Launches the game by making the console window visible.
     */
    public void start() {
        consoleWindow.setVisible(true);
    }

    /**
     * Begins the game using the game cycle manager.
     */
    public void beginGame() {
        cycleManager.beginGame();
    }

    /**
     * Resets the game via the game cycle manager.
     */
    public void resetGame() {
        cycleManager.resetGame();
    }

    /**
     * Handles a player command and executes the corresponding action.
     *
     * @param command The command string inputted by the player.
     */
    public void handleCommand(String command) {
        commandManager.handle(command, gameContext, consoleWindow);
    }

    /**
     * Retrieves the console window instance.
     *
     * @return The ConsoleWindow.
     */
    public ConsoleWindow getConsoleWindow() {
        return consoleWindow;
    }

    /**
     * Retrieves the GameContext containing core game data.
     *
     * @return The GameContext instance.
     */
    public GameContext getGameContext() {
        return gameContext;
    }
}






