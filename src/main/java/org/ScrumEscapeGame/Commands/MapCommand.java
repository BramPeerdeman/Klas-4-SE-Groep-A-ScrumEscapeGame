package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAEvents.RefreshMapEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.GameObjects.Room;

public class MapCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;

    public MapCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute() {
        // Publish a notification event indicating the refresh action.
        publisher.publish(new NotificationEvent("Refreshing graphical map view..."));

        // Publish an event signalling that the map should be refreshed.
        publisher.publish(new RefreshMapEvent());

        // Optionally, report the player's current room.
        int currentRoomId = context.getPlayer().getPosition();
        Room currentRoom = context.getRoomManager().getRooms().get(currentRoomId);
        if (currentRoom != null) {
            String message = String.format("You are in room %d.", currentRoom.getDisplayOrder());
            publisher.publish(new NotificationEvent(message));
        }
    }
}





