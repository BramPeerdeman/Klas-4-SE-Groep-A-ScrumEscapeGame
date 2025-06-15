package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Rooms.StartingRoom;

public class ToggleTerminalCommand implements Command {
    private final GameContext context;
    private final GameUIService uiService;

    public ToggleTerminalCommand(GameContext context, GameUIService uiService) {
        this.context = context;
        this.uiService = uiService;
    }

    @Override
    public void execute(String args) {
        int pos = context.getPlayer().getPosition();
        Room currentRoom = context.getRoomManager().getRooms().get(pos);

        // Special case for the starting room.
        if (currentRoom instanceof StartingRoom) {
            uiService.toggleTerminalPanel();
        } else if (currentRoom instanceof RoomWithQuestion && ((RoomWithQuestion) currentRoom).hasHelper()) {
            // Room supports the terminal/assistant. Toggle its display.
            uiService.toggleTerminalPanel();
        } else {
            // No terminal available in this room; notify the user.
            context.getEventPublisher().publish(new NotificationEvent("No terminal available!"));
        }
    }
}


