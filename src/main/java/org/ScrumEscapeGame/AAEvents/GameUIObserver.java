package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

/**
 * Observes game events and applies them to the UI.
 */
public class GameUIObserver extends UIObserverBase implements EventObserver<GameEvent> {


    /**
     * Constructs a GameUIObserver.
     *
     * @param uiService The UI service to interact with game events.
     */
    public GameUIObserver(GameUIService uiService) {
        super(uiService); // oproep naar de constructor van UIObserverBase
    }


    @Override
    public void update(GameEvent event) {
        event.apply(uiService);
    }
}


