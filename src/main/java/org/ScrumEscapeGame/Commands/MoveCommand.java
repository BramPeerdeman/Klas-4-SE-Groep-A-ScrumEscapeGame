package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.Connection;

import java.util.Optional;

/**
 * Executes movement commands in a specified direction.
 * Checks if there is a connection in the requested direction,
 * handles locked doors, and moves the player accordingly.
 */
public class MoveCommand implements Command {
    private String direction;
    private GameContext context;
    private final EventPublisher<GameEvent> publisher;
    private static final boolean DEBUG = false; // Set true for debug logging

    /**
     * Constructs a MoveCommand.
     *
     * @param direction the direction to move ("north", "south", "east", or "west").
     * @param context   the current game context.
     * @param publisher the event publisher for notifications.
     */
    public MoveCommand(String direction, GameContext context, EventPublisher<GameEvent> publisher) {
        this.direction = direction;
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute(String args) {
        // Retrieve the current room based on player position.
        Room currentRoom = context.getRoomManager().getRooms().get(context.getPlayer().getPosition());
        if (DEBUG) {
            System.out.println("DEBUG: Current room id: " + currentRoom.getId() +
                    " | Description: " + currentRoom.getDescription());
        }

        // Check for a neighbor in the specified direction.
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
            // Inform the player if the door is locked.
            publisher.publish(new NotificationEvent("The door in the " + direction +
                    " direction is locked. Answer the challenge to unlock it."));
        } else {
            // Move the player to the next room.
            Room nextRoom = connection.getDestination();
            if (DEBUG) {
                System.out.println("DEBUG: Next room id: " + nextRoom.getId());
            }
            context.getPlayer().setPosition(nextRoom.getId());
            // Call the room's onEnter to trigger any notifications or challenges.
            nextRoom.onEnter(context.getPlayer(), publisher);
        }
    }
}




