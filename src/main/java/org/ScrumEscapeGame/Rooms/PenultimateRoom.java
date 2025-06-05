package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;

import java.util.Collections;
import java.util.List;

/**
 * PenultimateRoom represents a room preceding the final boss challenge.
 * It might, for example, be configured to provide a preset inventory containing 6 keys.
 */
public class PenultimateRoom extends Room {
    // MOET NOG WORDEN AANGEPAST ALS JE JOKERS IN DEZE ROOM WIL
    @Override
    public List<Joker> getAvailableJokers() {
        return Collections.emptyList();
    }

    /**
     * Constructs a PenultimateRoom with a given id and description.
     *
     * @param id          the unique room identifier.
     * @param description a description of the room.
     */
    public PenultimateRoom(int id, String description) {
        super(id, description);
    }

    /**
     * Called when the player enters the penultimate room.
     * In addition to updating player's position, it notifies the player that
     * this room contains a special inventory (e.g., containing 6 keys).
     *
     * @param player    the player entering the room.
     * @param publisher the event publisher for sending notifications.
     */
    @Override
    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        super.onEnter(player, publisher);
        publisher.publish(new NotificationEvent("You have entered the penultimate room. This chamber is rumored to contain a special cache of keys."));
    }
}

