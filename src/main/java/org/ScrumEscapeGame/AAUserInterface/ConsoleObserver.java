package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.*;

public class ConsoleObserver extends UIObserverBase implements EventObserver<GameEvent> {


    public ConsoleObserver(GameUIService uiService) {
        super(uiService); // oproep naar de constructor van UIObserverBase
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof RoomEnteredEvent) {
            uiService.printMessage(((RoomEnteredEvent) event).getMessage());
        }
        // Handle other event types as needed
    }
}


