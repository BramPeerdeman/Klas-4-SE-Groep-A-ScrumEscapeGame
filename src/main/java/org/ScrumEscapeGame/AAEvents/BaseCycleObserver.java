package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAGame.GameCycleManager;

public abstract class BaseCycleObserver {
    protected GameCycleManager cycleManager;

    public BaseCycleObserver(GameCycleManager cycleManager) {
        this.cycleManager = cycleManager;
    }
}
