package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;

/**
 * Executes the "look" command.
 * When executed, it retrieves the current room's description
 * and notifies the player via a NotificationEvent.
 */
public class LookCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;

    /**
     * Constructs a LookCommand.
     *
     * @param context   the current game context.
     * @param publisher the event publisher for notifications.
     */
    public LookCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute(String args) {
        // Retrieve the current room via the player's position.
        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
        // Publish the room's description.
        publisher.publish(new NotificationEvent(currentRoom.getDescription()));
    }
}



