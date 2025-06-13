package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public abstract class UIObserverBase {
    protected GameUIService uiService;

    public UIObserverBase(GameUIService uiService) {
        this.uiService = uiService;
    }
}

