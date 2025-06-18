package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

/**
 * Represents an event clearing the output area.
 */
public class ClearOutputAreaEvent implements GameEvent {

    @Override
    public void apply(GameUIService uiService) {
        uiService.clearMessages();
    }
}
