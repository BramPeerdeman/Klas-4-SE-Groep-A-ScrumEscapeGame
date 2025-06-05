package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;

public interface PickupStrategy {
    boolean canPickup(Item item, Inventory source, Inventory destination);
}

