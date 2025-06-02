package org.ScrumEscapeGame.Items;
import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.GameObjects.Player;


public interface Unlockable {
    boolean unlock(Player player, EventPublisher<GameEvent> publisher);
}
