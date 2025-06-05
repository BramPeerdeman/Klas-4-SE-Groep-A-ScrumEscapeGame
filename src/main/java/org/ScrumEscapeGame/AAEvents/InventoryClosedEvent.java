package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class InventoryClosedEvent implements GameEvent {
    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage("Inventory panel closed.");
        // Re-enable commands.
        uiService.getCommandManager().setEnabled(true);
    }
}

