package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.BossRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Key represents a stackable key item that can be used (or "unlocked") to attempt unlocking a door.
 * It supports inspection and it is stackable by virtue of extending StackableItem.
 */
public class Key extends StackableItem implements Usable, Unlockable, Inspectable {

    /**
     * Constructs a new Key.
     *
     * @param id          the unique ID of the key.
     * @param name        the key's name.
     * @param description a description of the key.
     * @param quantity    the initial quantity of keys.
     */
    public Key(int id, String name, String description, int quantity) {
        super(id, name, description, quantity);
    }

    /**
     * Returns a human-friendly string that shows the key name and its current quantity.
     *
     * @return a string representation of the key.
     */
    @Override
    public String toString() {
        return getName() + " (x" + getQuantity() + ")";
    }

    /**
     * Returns a standardized description for the key.
     *
     * @return the description.
     */
    public String Description() {
        return "A key, rusted from being in an overgrown science lab. It can unlock a door, but loses power with use.";
    }

    /**
     * Implements inspection: publishes an event that provides details about the key.
     *
     * @param player    the player inspecting the key.
     * @param publisher the event publisher for notifying the system.
     */
    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), Description()));
    }

    @Override
    public boolean unlock(Player player, EventPublisher<GameEvent> publisher) {
        // This method is not used directly for unlocking the boss door,
        // Instead, our static helper is used to consume keys.
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        // Calls the unlock logic:
        return unlock(player, publisher);
    }

    /**
     * Static helper that attempts to consume exactly 'required' keys from the player's inventory.
     * If the total key quantity is at least the required amount, it subtracts that many from the stack(s)
     * and returns true; otherwise, it returns false without consuming any keys.
     *
     * @param player   the player.
     * @param required the number of keys required (e.g. 6).
     * @return true if keys were consumed; false otherwise.
     */
    public static boolean tryConsumeKeysForUnlock(Player player, int required) {
        int total = player.getInventory().getItems().stream()
                .filter(i -> i instanceof Key)
                .mapToInt(i -> ((Key) i).getQuantity())
                .sum();

        if (total < required) {
            return false;
        }

        int remaining = required;
        // Iterate over a copy of the inventory list so as not to get concurrent modification issues.
        for (Item item : new ArrayList<>(player.getInventory().getItems())) {
            if (item instanceof Key) {
                Key keyItem = (Key) item;
                int available = keyItem.getQuantity();
                if (available >= remaining) {
                    keyItem.removeQuantity(remaining);
                    remaining = 0;
                    break;
                } else {
                    keyItem.removeQuantity(available);
                    remaining -= available;
                }
            }
        }

        // Optionally, remove any key instance with quantity 0.
        List<Item> updated = player.getInventory().getItems().stream()
                .filter(i -> !(i instanceof Key) || ((Key) i).getQuantity() > 0)
                .collect(Collectors.toList());
        player.getInventory().setItems(updated);

        return remaining == 0;
    }
}





