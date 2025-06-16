package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

// TerminalClosedEvent.java
public class TerminalClosedEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        // Re-enable normal commands.
        uiService.getCommandManager().setEnabled(true);
    }
}

