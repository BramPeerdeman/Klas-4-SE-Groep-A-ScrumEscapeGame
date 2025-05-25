package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

/**
 * Represents an event that refreshes the graphical map view.
 */
public class RefreshMapEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        uiService.refreshMapView();
    }
}


