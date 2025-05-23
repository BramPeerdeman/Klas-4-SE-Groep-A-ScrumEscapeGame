package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.Connection;

import java.util.HashMap;
import java.util.Optional;

public class MoveCommand implements Command {
    private String direction;
    private GameContext context;
    private static final boolean DEBUG = true; // set true for debug logging

    private EventPublisher<GameEvent> publisher;

    public MoveCommand(String direction, GameContext context, EventPublisher<GameEvent> publisher) {
        this.direction = direction;
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute() {
        Room currentRoom = context.getRoomManager().getRooms().get(context.getPlayer().getPosition());
        if (DEBUG) {
            System.out.println("DEBUG: Current room id: " + currentRoom.getId() +
                    " | Description: " + currentRoom.getDescription());
        }

        Optional<Connection> neighborConnectionOpt = currentRoom.getNeighbour(direction);

        if (!neighborConnectionOpt.isPresent()) {
            if (DEBUG) {
                System.out.println("DEBUG: There is no neighbor connection in the direction: " + direction);
            }
            publisher.publish(new NotificationEvent("You can't go " + direction + " from here."));
            return;
        }

        Connection connection = neighborConnectionOpt.get();
        if (DEBUG) {
            System.out.println("DEBUG: Found neighbor connection: " + connection);
            System.out.println("DEBUG: Connection canPass? " + connection.canPass());
        }

        if (!connection.canPass()) {
            publisher.publish(new NotificationEvent("The door in the " + direction +
                    " direction is locked. Answer the challenge to unlock it."));
        } else {
            Room nextRoom = connection.getDestination();
            if (DEBUG) {
                System.out.println("DEBUG: Next room id: " + nextRoom.getId());
            }
            context.getPlayer().setPosition(nextRoom.getId());
            // Note: the onEnter method should also use the publisher rather than directly printing.
            nextRoom.onEnter(context.getPlayer(), publisher);
        }
    }
}



