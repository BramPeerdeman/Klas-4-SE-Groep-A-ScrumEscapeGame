package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class InventoryItemActionEvent implements GameEvent {
    private final int itemId;
    private final String name;
    private final String source; // "Room" or "Player"
    private final String action; // "pickup" or "use"

    public InventoryItemActionEvent(int itemId, String name, String source, String action) {
        this.itemId = itemId;
        this.name = name;
        this.source = source;
        this.action = action;
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

    public String getAction() {
        return action;
    }

    @Override
    public void apply(GameUIService uiService) {
        // Inform the system that the user has confirmed an action on the item.
        uiService.printMessage("Action confirmed: " + action + " for " + name + " from " + source + " inventory.");
        // Here you might invoke the corresponding command (for example, call the PickupCommand or UseCommand).
    }
}

