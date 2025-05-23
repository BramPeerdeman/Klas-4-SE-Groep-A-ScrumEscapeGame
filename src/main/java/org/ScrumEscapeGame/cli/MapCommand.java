package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;

public class MapCommand implements Command {
    private Player player;

    public MapCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        // Inform the player that the graphical map is being refreshed.
        Game.consoleWindow.printMessage("Refreshing graphical map view...");

        // Refresh the map coordinates in case the room map has changed.
        Game.consoleWindow.getMapPanel().refreshCoordinates();

        // Repaint the panel so the latest state (including the player's position) is drawn.
        Game.consoleWindow.getMapPanel().repaint();

        // Optionally, report the player's current room.
        int currentRoomId = player.getPosition();
        String message = String.format("You are in room %d.", Game.rooms.get(currentRoomId).getDisplayOrder());
        Game.consoleWindow.printMessage(message);
    }
}




