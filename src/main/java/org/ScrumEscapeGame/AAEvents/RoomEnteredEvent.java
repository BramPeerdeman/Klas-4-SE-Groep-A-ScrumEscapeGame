package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class RoomEnteredEvent implements GameEvent {
    private final String message;

    public RoomEnteredEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage(message);
    }
}
