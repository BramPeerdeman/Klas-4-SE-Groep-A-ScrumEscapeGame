package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.Connection;

import java.util.HashMap;
import java.util.Optional;

public class MoveCommand implements Command {
    private String direction;
    private Player player;
    private HashMap<Integer, Room> rooms;
    private static final boolean DEBUG = true; // set true for debug logging

    public MoveCommand(String direction, Player player, HashMap<Integer, Room> rooms) {
        this.direction = direction;
        this.player = player;
        this.rooms = rooms;
    }

    @Override
    public void execute() {
        Room currentRoom = rooms.get(player.getPosition());
        if (DEBUG) {
            System.out.println("DEBUG: Current room id: " + currentRoom.getId() +
                    " | Description: " + currentRoom.getDescription());
        }

        Optional<Connection> neighborConnectionOpt = currentRoom.getNeighbour(direction);

        if (!neighborConnectionOpt.isPresent()) {
            if (DEBUG) {
                System.out.println("DEBUG: There is no neighbor connection in the direction: " + direction);
            }
            Game.consoleWindow.printMessage("You can't go " + direction + " from here.");
            return;
        }

        Connection connection = neighborConnectionOpt.get();
        if (DEBUG) {
            System.out.println("DEBUG: Found neighbor connection: " + connection);
            System.out.println("DEBUG: Connection canPass? " + connection.canPass());
        }

        if (!connection.canPass()) {
            Game.consoleWindow.printMessage("The door in the " + direction +
                    " direction is locked. Answer the challenge to unlock it.");
        } else {
            Room nextRoom = connection.getDestination();
            if (DEBUG) {
                System.out.println("DEBUG: Next room id: " + nextRoom.getId());
            }
            player.setPosition(nextRoom.getId());
            nextRoom.onEnter(player);
        }
    }
}


