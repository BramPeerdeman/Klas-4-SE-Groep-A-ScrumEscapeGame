package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAGame.GameCycleManager;

/**
 * Observes game start events and triggers initialization.
 */
public class GameBeginObserver implements EventObserver<GameEvent> {
    private final GameCycleManager cycleManager;

    /**
     * Constructs a GameBeginObserver.
     *
     * @param cycleManager The GameCycleManager that handles game initialization.
     */
    public GameBeginObserver(GameCycleManager cycleManager) {
        this.cycleManager = cycleManager;
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof GameBeginEvent) {
            cycleManager.beginGame();
        }
    }
}


