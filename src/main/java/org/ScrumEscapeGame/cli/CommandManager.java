package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.cli.Command;
import org.ScrumEscapeGame.cli.ConsoleWindow;
import org.ScrumEscapeGame.cli.GameContext;

import java.util.HashMap;
import java.util.Map;

public class CommandManager
{
    private final Map<String, Command> commands = new HashMap<>();

    public void register(String key, Command command) {
        commands.put(key, command);
    }

    public void handle(String key, GameContext context, ConsoleWindow console) {
        Command cmd = commands.get(key);
        if (cmd != null) {
            cmd.execute(); // Optionally inject context to command
        } else {
            console.printMessage("Unknown command: " + key);
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
