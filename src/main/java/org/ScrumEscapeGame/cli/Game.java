package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Handlers.GameReset;
import org.ScrumEscapeGame.Handlers.GameStart;
import org.ScrumEscapeGame.Rooms.RoomFactory;
import org.ScrumEscapeGame.Rooms.RoomMapBuilder;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Rooms.StartingRoom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Game()
    {
        this.player = new Player();
        this.roomManager = new RoomManager();
        this.commandManager = new CommandManager();
        this.gameContext = new GameContext(player, roomManager);
        this.consoleWindow = new ConsoleWindow(this);
        this.cycleManager = new GameCycleManager(gameContext, consoleWindow);
    }

    public void start()
    {
        consoleWindow.setVisible(true);
    }

    public void beginGame()
    {
        cycleManager.beginGame(commandManager);
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



