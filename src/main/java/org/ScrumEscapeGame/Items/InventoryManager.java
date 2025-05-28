package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.GameObjects.Inventory;

public class InventoryManager {
    private PickupStrategy pickupStrategy = new DefaultPickupStrategy();

    public void setPickupStrategy(PickupStrategy strategy) {
        this.pickupStrategy = strategy;
    }

    /**
     * Transferring an item between inventories.
     */
    public boolean transferItem(Item item, Inventory from, Inventory to, EventPublisher<GameEvent> publisher) {
        if (pickupStrategy.canPickup(item, from, to)) {
            from.removeItem(item);
            to.addItem(item);
            publisher.publish(new NotificationEvent(item.getName() + " was transferred."));
            return true;
        }
        return false;
    }
}


