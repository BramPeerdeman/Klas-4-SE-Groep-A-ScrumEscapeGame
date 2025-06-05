package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.EventObserver;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.RoomEnteredEvent;

public class ConsoleObserver implements EventObserver<GameEvent> {
    private GameUIService uiService;

    public ConsoleObserver(GameUIService uiService) {
        this.uiService = uiService;
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof RoomEnteredEvent) {
            uiService.printMessage(((RoomEnteredEvent) event).getMessage());
        }
        // Handle other event types as needed
    }
}


