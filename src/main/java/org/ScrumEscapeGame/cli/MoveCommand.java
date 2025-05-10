package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

import java.util.HashMap;
import java.util.Objects;

public class MoveCommand implements Command{
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
        currentRoom.getNeighbour(direction).orElse(currentRoom).onEnter(player); //orElse zorgt ervoor dat als het null is dan wordt je naar de huidige kamer gestuurd.
    }
}
