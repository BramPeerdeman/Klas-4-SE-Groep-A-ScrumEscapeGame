package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;

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
        String map = "Map of the game:\n"
                + "  _______   _______   _______ \n"
                + " |       | |       | |       |\n"
                + " |   1   |-|   2   |-|   3   |\n"
                + " |_______| |_______| |_______|\n"
                + String.format("You are at room %s", Game.rooms.get(player.getPosition()).getId());
        Game.consoleWindow.printMessage(map);
    }
}
