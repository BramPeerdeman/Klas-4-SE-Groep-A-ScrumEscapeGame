package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class UnlockAttemptEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage("You attempt to unlock the boss room...");
    }
}
