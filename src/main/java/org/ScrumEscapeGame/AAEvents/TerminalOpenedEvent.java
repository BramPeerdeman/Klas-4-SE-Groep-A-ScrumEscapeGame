package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

// TerminalOpenedEvent.java
public class TerminalOpenedEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        // As an example, disable normal commands when the assistant is active.
        uiService.getCommandManager().setEnabled(false);
    }
}

