package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.AAGame.Game;

/**
 * Executes the "answer" command.
 * Determines the current room, and if it is a RoomWithQuestion,
 * triggers the room’s challenge. Otherwise, notifies the player that
 * there is no challenge in the current room.
 */
public class AnswerCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;
    private final DisplayService displayService; // Used by room strategies

    /**
     * Constructs an AnswerCommand.
     *
     * @param context       the current game context.
     * @param publisher     the event publisher to broadcast notifications.
     * @param displayService the display service for UI output.
     */
    public AnswerCommand(GameContext context, EventPublisher<GameEvent> publisher, DisplayService displayService) {
        this.context = context;
        this.publisher = publisher;
        this.displayService = displayService;
    }

    @Override
    public void execute() {
        // Get the current room based on the player's position.
        int pos = context.getPlayer().getPosition();
        Room currentRoom = context.getRoomManager().getRooms().get(pos);
        if (currentRoom instanceof RoomWithQuestion) {
            // Trigger the question using the provided displayService.
            ((RoomWithQuestion) currentRoom)
                    .triggerQuestion(context.getPlayer(), context.getEventPublisher(), displayService);
        } else {
            // Inform the player that no challenge exists in this room.
            publisher.publish(new NotificationEvent("There is no challenge to answer in this room."));
        }
    }
}




