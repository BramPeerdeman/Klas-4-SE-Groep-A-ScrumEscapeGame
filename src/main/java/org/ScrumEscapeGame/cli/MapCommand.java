package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

class MapCommand implements Command {
    private Player player;
    public MapCommand(Player player) {
        this.player = player;
    }

    public void execute() {
        Game.consoleWindow.printMessage("Displaying map...");
        displayMap();
    }

    public void displayMap() {
        // Build the base ASCII art representing the static map layout.
        String map = "Map of the game:\n"
                + "  _______   _______   _______ \n"
                + " |       | |       | |       |\n"
                + " |   1   |-|   2   |-|   3   |\n"
                + " |_______| |_______| |_______|\n";

        // Retrieve the player's current room.
        int currentRoomId = player.getPosition();
        Room currentRoom = Game.rooms.get(currentRoomId);

        // Append the current room info.
        if (currentRoom != null) {
            map += String.format("You are at room %s", currentRoom.getId());
        } else {
            map += "Current room not found in the map.";
        }

        // Print the map to the console window.
        Game.consoleWindow.printMessage(map);
    }
}
