package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;

/**
 * PresetInventory is a specialized Inventory that supports stacking.
 * If an item being added is stackable and an item with the same ID already exists,
 * its quantity is increased instead of adding a duplicate entry.
 */
public class PresetInventory extends Inventory {

    /**
     * Adds an item to the inventory. If the item supports stacking
     * and an item with the same ID already exists, their quantities are merged.
     *
     * @param item The item to add.
     */
    @Override
    public void addItem(Item item) {
        // Check if the item is stackable.
        if (item.isStackable() && item instanceof StackableItem) {
            for (Item existingItem : getItems()) {
                if (existingItem.getId() == item.getId() && existingItem.isStackable()) {
                    // Merge quantities.
                    ((StackableItem) existingItem).addQuantity(((StackableItem) item).getQuantity());
                    return;
                }
            }
        }
        // Item not found in inventory, or not stackable—just add it.
        super.addItem(item);
    }
}


