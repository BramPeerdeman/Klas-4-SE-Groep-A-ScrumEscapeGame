package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAGame.RoomManager;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.GameObjects.Room;

public class LookCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;

    public LookCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute() {
        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
        publisher.publish(new NotificationEvent(currentRoom.getDescription()));
    }
}


