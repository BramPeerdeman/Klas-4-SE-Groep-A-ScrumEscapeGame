package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class RefreshMapEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        uiService.refreshMapView();
    }
}

