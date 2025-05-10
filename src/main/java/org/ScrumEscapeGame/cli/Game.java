package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

import java.util.HashMap;
import java.util.Map;

public class Game
{
    private static Map<String, Command> commands = new HashMap<>();
    public static ConsoleWindow consoleWindow;

    static Player player = new Player();
    /*Huidig gebruikt elke command de spelerspositie en dus elke command heeft een spelerconstructor.
    Kan iemand vinden of dat beter behandelt kan worden?*/

    static HashMap<Integer, Room> rooms = new HashMap<>(); //Hiermee kunnen we in main de map aanmaken.

    public void start() {
        commands.put("look", new LookCommand(player));
        commands.put("map", new MapCommand(player));
        commands.put("status", new StatusCommand(player));
        /*LET OP: dit zijn de commands voor het bewegen, ik maak gebruik van WASD*/
        commands.put("a", new MoveCommand("west", player, rooms));
        commands.put("d", new MoveCommand("east", player, rooms));

        consoleWindow = new ConsoleWindow();
        consoleWindow.setVisible(true);

        consoleWindow.printWelcomeMessage();
    }

    public static void handleCommand(String command) {
        Command cmd = commands.get(command);
        if (cmd != null) {
            cmd.execute();
        } else {
            consoleWindow.printMessage("Unknown command: " + command);
        }
    }

    public HashMap<Integer, Room> getRooms() {
        return rooms;
    }
}



