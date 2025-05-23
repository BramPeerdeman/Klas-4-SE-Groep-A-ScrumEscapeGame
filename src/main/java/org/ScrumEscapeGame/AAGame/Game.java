package org.ScrumEscapeGame.AAGame;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAUserInterface.ConsoleWindow;
import org.ScrumEscapeGame.Commands.CommandManager;
import org.ScrumEscapeGame.GameObjects.Player;

public class Game
{
//    private static Map<String, Command> commands = new HashMap<>();
//    public static ConsoleWindow consoleWindow;
//
//    public static Player player = new Player();
//    /*Huidig gebruikt elke command de spelerspositie en dus elke command heeft een spelerconstructor.
//    Kan iemand vinden of dat beter behandelt kan worden?*/
//
//    public static HashMap<Integer, Room> rooms = new HashMap<>(); //Hiermee kunnen we in main de map aanmaken.

    private final CommandManager commandManager;
    private final RoomManager roomManager;
    private final GameCycleManager cycleManager;
    private final GameContext gameContext;
    private final Player player;
    private final ConsoleWindow consoleWindow;
    private final EventPublisher<GameEvent> publisher;


    public Game()
    {
        this.player = new Player();
        this.roomManager = new RoomManager();
        this.commandManager = new CommandManager();
        this.publisher = new EventPublisher<>();
        this.gameContext = new GameContext(player, roomManager, publisher);
        this.consoleWindow = new ConsoleWindow(gameContext);
        this.cycleManager = new GameCycleManager(gameContext, consoleWindow, commandManager, publisher);
    }

    public void start()
    {
        consoleWindow.setVisible(true);
    }

    public void beginGame()
    {
        cycleManager.beginGame();
    }

    /**
     * Resets the game when a question is answered wrong.
     * This clears the current room map, re-shuffles the questions, and sends the
     * player to the start room.
     */
    public void resetGame()
    {
        cycleManager.resetGame();
    }

    public void handleCommand(String command) {
        commandManager.handle(command, gameContext, consoleWindow);
    }

//    public HashMap<Integer, Room> getRooms() {
//        return rooms;
//    }

    public ConsoleWindow getConsoleWindow() {
        return consoleWindow;
    }

    public GameContext getGameContext() {
        return gameContext;
    }
}



