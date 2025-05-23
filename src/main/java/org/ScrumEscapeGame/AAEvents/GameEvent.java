package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public interface GameEvent {
    void apply(GameUIService uiService);
}



