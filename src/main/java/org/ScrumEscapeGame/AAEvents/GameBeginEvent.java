package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class GameBeginEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        // Optionally, the event can update the UI (if needed),
        // but its main purpose is to serve as a signal.
    }
}

