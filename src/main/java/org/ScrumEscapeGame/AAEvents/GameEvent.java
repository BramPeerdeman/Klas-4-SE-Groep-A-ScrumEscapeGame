package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

/**
 * Represents a generic game event.
 * Each event must implement an apply method that interacts with the UI.
 */
public interface GameEvent {
    /**
     * Processes the event using the given UI service.
     *
     * @param uiService The UI service to interact with.
     */
    void apply(GameUIService uiService);
}



