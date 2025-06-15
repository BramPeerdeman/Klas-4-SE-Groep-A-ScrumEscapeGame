package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

// TerminalObserver.java
public class TerminalObserver extends UIObserverBase implements EventObserver<GameEvent> {

    public TerminalObserver(GameUIService uiService) {
        super(uiService);
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof TerminalOpenedEvent || event instanceof TerminalClosedEvent) {
            // Apply the event's changes.
            event.apply(uiService);
        }
    }
}

