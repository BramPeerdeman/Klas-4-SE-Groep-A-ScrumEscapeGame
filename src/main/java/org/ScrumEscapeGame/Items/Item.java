package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.GameObjects.Player;

abstract public class Item {

    public abstract void inspect(Player player, EventPublisher<GameEvent> publisher);

    public abstract boolean unlock(String doorId);
}
