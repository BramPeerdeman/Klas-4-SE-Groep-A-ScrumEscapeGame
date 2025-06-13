package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;

public class HintCommand implements Command {
    private final GameContext context;
    private final GameUIService uiService;

    public HintCommand(GameContext context, GameUIService uiService) {
        this.context = context;
        this.uiService = uiService;
    }

    @Override
    public void execute(String args) {
        int pos = context.getPlayer().getPosition();
        Room currentRoom = context.getRoomManager().getRooms().get(pos);
        if (currentRoom instanceof RoomWithQuestion) {
            if (((RoomWithQuestion) currentRoom).hasHelper()) {
                ((RoomWithQuestion) currentRoom).giveHint(context.getEventPublisher());
            }
        } else {
            context.getEventPublisher().publish(new NotificationEvent("No hints available!"));
        }
    }
}
