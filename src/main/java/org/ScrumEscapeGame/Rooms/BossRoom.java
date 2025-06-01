package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

/**
 * BossRoom represents a room where the final challenge is presented.
 * It contains a specialized boss door that requires keys to unlock.
 */
public class BossRoom extends Room {
    // The locked door that guards the boss challenge.
    private LockedDoor bossDoor;

    /**
     * Constructs a BossRoom with a given id, description, and boss door.
     *
     * @param id          the unique room identifier.
     * @param description a description of the room.
     * @param bossDoor    the locked door instance guarding the boss challenge.
     */
    public BossRoom(int id, String description, LockedDoor bossDoor) {
        super(id, description);
        this.bossDoor = bossDoor;
    }

    /**
     * Returns the boss door associated with this room.
     *
     * @return the LockedDoor instance for this boss room.
     */
    public LockedDoor getBossDoor() {
        return bossDoor;
    }

    /**
     * Called when the player enters the boss room.
     * In addition to the standard room entry logic, it publishes a notification
     * informing the player that the door requires keys to unlock.
     *
     * @param player    the player entering the room.
     * @param publisher the event publisher for sending notifications.
     */
    @Override
    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        // Let the base class update the player's position and notify entry.
        super.onEnter(player, publisher);
        // Inform the player specific to the boss room.
        publisher.publish(new NotificationEvent("You have entered the boss room. To unlock the door, you must use your keys."));
    }
}


