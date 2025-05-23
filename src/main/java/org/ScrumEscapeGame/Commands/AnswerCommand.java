package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.AAGame.Game;

public class AnswerCommand implements Command {
    private final GameContext context;
    private EventPublisher<GameEvent> publisher;

    public AnswerCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute() {
        int pos = context.getPlayer().getPosition();
        Room currentRoom = context.getRoomManager().getRooms().get(pos);
        if (currentRoom instanceof RoomWithQuestion) {
            ((RoomWithQuestion) currentRoom).triggerQuestion(context.getPlayer());
        } else {
            publisher.publish(new NotificationEvent("There is no challenge to answer in this room."));
        }
    }
}


