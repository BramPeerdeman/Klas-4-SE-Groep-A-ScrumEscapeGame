package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

/**
 * Represents an event triggered upon entering a room.
 */
public class RoomEnteredEvent implements GameEvent {
    private final String message;

    /**
     * Constructs a RoomEnteredEvent.
     *
     * @param message The message to display.
     */
    public RoomEnteredEvent(String message) {
        this.message = message;
    }

    /**
     * Returns the message for this event.
     *
     * @return The room entry message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage(message);
    }
}

