package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

import java.util.HashMap;
import java.util.Optional;

public class MoveCommand implements Command {
    private String direction;
    private Player player;
    private HashMap<Integer, Room> rooms;

    public MoveCommand(String direction, Player player, HashMap<Integer, Room> rooms) {
        this.direction = direction;
        this.player = player;
        this.rooms = rooms;
    }

    @Override
    public void execute() {
        Room currentRoom = rooms.get(player.getPosition());
        Optional<Room> neighbour = currentRoom.getNeighbour(direction);

        if (neighbour.isPresent()) {
            Room nextRoom = neighbour.get();
            nextRoom.onEnter(player);
        } else {
            Game.consoleWindow.printMessage("You can't go " + direction + " from here.");
        }
    }
}
