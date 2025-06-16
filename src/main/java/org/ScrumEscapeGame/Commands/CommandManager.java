package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAUserInterface.ConsoleWindow;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;

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
        if (!enabled && !key.equalsIgnoreCase("toggleInventory")) {
            console.printMessage("Commands disabled while inventory is open.");
            return;
        }
        Command cmd = commands.get(key);

        // Before executing a move command, check if the current room is safe.
        if (isMovementCommand(key)) {
            Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
            if (currentRoom instanceof RoomWithQuestion) {
                RoomWithQuestion rq = (RoomWithQuestion) currentRoom;
                if (!rq.canLeave()) {
                    console.printMessage("You must defeat the monster or overcome the challenge before leaving!");
                    return;
                }
            }
        }

        if (cmd != null) {
            cmd.execute(args);
        } else {
            console.printMessage("Unknown command: " + key);
        }
    }

    /**
     * Checks whether the provided key is a movement command.
     * This method can be extended if you introduce alternative naming conventions.
     *
     * @param key the command key provided (e.g., "w", "moveNorth")
     * @return true if the key corresponds to a movement command, false otherwise.
     */
    public boolean isMovementCommand(String key) {
        if (key == null) {
            return false;
        }

        // Normalize the key to lower case for easier comparison.
        String normalizedKey = key.trim().toLowerCase();

        // Check for single-letter commands (W, A, S, D)
        if (normalizedKey.equals("w") || normalizedKey.equals("a") ||
                normalizedKey.equals("s") || normalizedKey.equals("d")) {
            return true;
        }

        // Check for mnemonic names from our input bindings.
        // For example: "movenorth", "movewest", "movesouth", "moveeast"
        if (normalizedKey.equals("movenorth") || normalizedKey.equals("movewest") ||
                normalizedKey.equals("movesouth") || normalizedKey.equals("moveeast")) {
            return true;
        }

        return false;
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

