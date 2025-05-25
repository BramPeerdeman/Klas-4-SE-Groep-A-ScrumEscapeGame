package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAEvents.RefreshMapEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.GameObjects.Room;

/**
 * Executes the "map" command.
 * This command refreshes the graphical map view and notifies the player
 * of their current room (by display order).
 */
public class MapCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;

    /**
     * Constructs a MapCommand.
     *
     * @param context   the current game context.
     * @param publisher the event publisher for broadcasting notifications.
     */
    public MapCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute() {
        // Publish a notification about refreshing the map view.
        publisher.publish(new NotificationEvent("Refreshing graphical map view..."));
        // Trigger a map refresh.
        publisher.publish(new RefreshMapEvent());

        // Optionally notify the player of their current room.
        int currentRoomId = context.getPlayer().getPosition();
        Room currentRoom = context.getRoomManager().getRooms().get(currentRoomId);
        if (currentRoom != null) {
            String message = String.format("You are in room %d.", currentRoom.getDisplayOrder());
            publisher.publish(new NotificationEvent(message));
        }
    }
}






