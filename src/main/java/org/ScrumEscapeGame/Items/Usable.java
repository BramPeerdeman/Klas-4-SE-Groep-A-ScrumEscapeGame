package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.GameObjects.Player;

public interface Usable {
    /**
     * Executes the unique effect of this item.
     *
     * @param player the player using the item
     * @param publisher the event publisher to notify the system
     * @return true if the item was used successfully.
     */
    boolean use(Player player, EventPublisher<GameEvent> publisher);
}

