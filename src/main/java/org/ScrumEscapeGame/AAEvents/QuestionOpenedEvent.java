package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class QuestionOpenedEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        uiService.getCommandManager().setEnabled(false);
    }
}
