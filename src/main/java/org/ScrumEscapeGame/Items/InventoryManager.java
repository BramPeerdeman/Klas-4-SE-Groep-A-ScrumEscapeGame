package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;

public class InventoryManager {
    private PickupStrategy pickupStrategy;

    public InventoryManager(PickupStrategy strategy) {
        this.pickupStrategy = strategy;
    }

    public void setPickupStrategy(PickupStrategy strategy) {
        this.pickupStrategy = strategy;
    }

    /**
     * Attempts to transfer an item from one inventory to another.
     */
    public boolean transferItem(Item item, Inventory from, Inventory to) {
        if (pickupStrategy.canPickup(item, from, to)) {
            from.removeItem(item);
            to.addItem(item);
            // Notify the event system about the change
            // EventSystem.notify("ItemTransferred", item);
            return true;
        }
        return false;
    }
}

