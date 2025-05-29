package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class InventoryItemHoveredEvent implements GameEvent {
    private final int itemId;
    private final String name;
    private final String description;
    private final String source; // "Room" or "Player"

    public InventoryItemHoveredEvent(int itemId, String name, String description, String source) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.source = source;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSource() {
        return source;
    }

    @Override
    public void apply(GameUIService uiService) {
        // For example: update a tooltip or side panel description.
        //add later with for example, change status of ui or something.
    }
}

