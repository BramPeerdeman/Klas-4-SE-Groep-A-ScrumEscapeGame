package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class InventoryOpenedEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        // Disable normal commands (all except inventory toggle) by updating the CommandManager.
        uiService.getCommandManager().setEnabled(false);
    }
}
