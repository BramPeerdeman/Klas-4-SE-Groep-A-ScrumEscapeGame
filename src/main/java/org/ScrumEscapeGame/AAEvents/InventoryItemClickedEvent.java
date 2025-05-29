package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class InventoryItemClickedEvent implements GameEvent {
    private final int itemId;
    private final String name;
    private final String source; // "Room" or "Player"

    public InventoryItemClickedEvent(int itemId, String name, String source) {
        this.itemId = itemId;
        this.name = name;
        this.source = source;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    @Override
    public void apply(GameUIService uiService) {
        if (source.equalsIgnoreCase("room")) {
            //nothing
        } else if (source.equalsIgnoreCase("player")) {
            //nothing
        }
    }
}

