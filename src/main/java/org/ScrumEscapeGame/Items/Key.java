package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.GameObjects.Player;

public class Key extends StackableItem implements Unlockable, Inspectable {

    public Key(int id, String name, String description, int quantity) {
        super(id, name, description, quantity);
    }

    /**
     * Returns a standardized description for the key.
     */
    public String Description() {
        return "A key, rusted from being in an overgrown science lab. It seems to still be able to unlock a door.";
    }

    /**
     * Publishes an item inspection event when the key is inspected.
     */
    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), Description()));
    }

    /**
     * Tries to unlock the boss door that the player is adjacent to.
     *
     * In our decoupled design only boss doors require keys. Rather than trying to
     * determine which door to unlock directly, the key simply publishes an event
     * (UnlockAttemptEvent) that delegates the full unlocking logic to a mediator or observer.
     *
     * This means that the key does not need to know about door IDs or hold any door references.
     *
     * @param player    The player using the key.
     * @param publisher An event publisher to notify the system about the unlock attempt.
     * @return true to indicate that an unlock attempt was initiated.
     */
    @Override
    public boolean unlock(Player player, EventPublisher<GameEvent> publisher) {
        // Publish an event to attempt unlocking the boss door.
        publisher.publish(new UnlockAttemptEvent());
        // The mediator/observer (which listens for UnlockAttemptEvent) will examine the player's
        // current room, count keys in the inventory, and perform the unlocking if the key threshold is met.
        return true;
    }
}



