package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

/**
 * Observes game events and applies them to the UI.
 */
public class GameUIObserver implements EventObserver<GameEvent> {
    private final GameUIService uiService;

    /**
     * Constructs a GameUIObserver.
     *
     * @param uiService The UI service to interact with game events.
     */
    public GameUIObserver(GameUIService uiService) {
        this.uiService = uiService;
    }

    @Override
    public void update(GameEvent event) {
        event.apply(uiService);
    }
}


