package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;

public class DefaultPickupStrategy implements PickupStrategy {
    @Override
    public boolean canPickup(Item item, Inventory source, Inventory destination) {
        // Basic check to see if the item exists in the source inventory.
        return source.getItems().contains(item);
    }
}
