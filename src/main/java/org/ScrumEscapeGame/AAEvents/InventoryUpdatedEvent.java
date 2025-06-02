package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.AAUserInterface.InventoryPanel;

public class InventoryUpdatedEvent implements GameEvent {
    private final String source; // e.g., "room" or "player"

    public InventoryUpdatedEvent(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage("Inventory updated for " + source + " inventory.");
        // Optionally, if the inventory panel is visible, force it to refresh.
        if (uiService.isInventoryVisible()) {
            uiService.getInventoryPanel().refresh();
        }
    }
}

