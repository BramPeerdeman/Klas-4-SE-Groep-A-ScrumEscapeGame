package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAUserInterface.ConsoleWindow;
import org.ScrumEscapeGame.AAGame.GameContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages game commands by mapping string keys to Command objects.
 * Commands can be registered and later invoked based on user input.
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private boolean enabled = true; // by default, commands are enabled

    /**
     * Registers a command under a specified key.
     *
     * @param key     the key (e.g., "look", "map", "w" etc.) associated with the command.
     * @param command the Command object to register.
     */
    public void register(String key, Command command) {
        commands.put(key, command);


    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Retrieves and executes the command associated with the key.
     * If no command is found, prints an error message via the console.
     *
     * @param key     the command key.
     * @param context the game context.
     * @param console the console window used to output messages.
     */
    public void handle(String key, GameContext context, ConsoleWindow console, String args) {
        // Always allow the toggleInventory command.
        if (!enabled && !key.equalsIgnoreCase("toggleInventory")) {
            console.printMessage("Commands disabled while inventory is open.");
            return;
        }
        Command cmd = commands.get(key);
        if (cmd != null) {
            cmd.execute(args);
        } else {
            console.printMessage("Unknown command: " + key);
        }
    }


    /**
     * Returns the mapping of registered commands.
     *
     * @return a Map of command keys to Command objects.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}

