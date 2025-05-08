package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;

import java.util.HashMap;
import java.util.Map;

public class Game
{
    private static Map<String, Command> commands = new HashMap<>();
    public static ConsoleWindow consoleWindow;

    static Player player = new Player();

    public static void main(String[] args) {
        commands.put("look", new LookCommand());
        commands.put("map", new MapCommand());
        commands.put("status", new StatusCommand(player));

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
}



