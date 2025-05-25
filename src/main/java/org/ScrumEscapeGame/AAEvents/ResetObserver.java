package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAGame.GameCycleManager;

/**
 * Observes reset events and triggers a game reset when needed.
 */
public class ResetObserver implements EventObserver<GameEvent> {
    private final GameCycleManager cycleManager;

    /**
     * Constructs a ResetObserver.
     *
     * @param cycleManager The manager responsible for handling game resets.
     */
    public ResetObserver(GameCycleManager cycleManager) {
        this.cycleManager = cycleManager;
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof GameResetEvent) {
            cycleManager.resetGame();
        }
    }
}


