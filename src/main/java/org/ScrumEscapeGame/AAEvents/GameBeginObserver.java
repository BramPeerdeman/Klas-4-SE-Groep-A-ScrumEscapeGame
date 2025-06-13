package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAGame.GameCycleManager;

/**
 * Observes game start events and triggers initialization.
 */
public class GameBeginObserver extends BaseCycleObserver implements EventObserver<GameEvent> {


    /**
     * Constructs a GameBeginObserver.
     *
     * @param cycleManager The GameCycleManager that handles game initialization.
     */
    public GameBeginObserver(GameCycleManager cycleManager) {
        super(cycleManager);
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof GameBeginEvent) {
            cycleManager.beginGame();
        }
    }
}


