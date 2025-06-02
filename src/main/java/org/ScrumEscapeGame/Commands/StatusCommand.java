package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;

/**
 * Executes the "status" command.
 * It informs the player of their current room (by room ID) and their overall status.
 */
public class StatusCommand implements Command {
    private GameContext context;
    private EventPublisher<GameEvent> publisher;

    /**
     * Constructs a StatusCommand.
     *
     * @param context   the current game context.
     * @param publisher the event publisher for notifications.
     */
    public StatusCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute(String args) {
        // Notify the player of the current room number.
        publisher.publish(new NotificationEvent("You are currently in room number: " + context.getPlayer().getPosition()));
        // Notify the player of their current status.
        publisher.publish(new NotificationEvent("Your current status is: " + context.getPlayer().getStatus()));
    }
}

