package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

/**
 * Represents a notification event, typically used for system messages.
 */
public class NotificationEvent implements GameEvent {
    private final String message;

    /**
     * Constructs a NotificationEvent.
     *
     * @param message The message to display.
     */
    public NotificationEvent(String message) {
        this.message = message;
    }

    /**
     * Returns the message stored in the event.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage(message);
    }
}


