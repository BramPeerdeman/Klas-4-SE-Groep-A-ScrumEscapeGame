package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.ItemInspectEvent;
import org.ScrumEscapeGame.AAEvents.UseItemEvent;
import org.ScrumEscapeGame.GameObjects.Player;

public class TestItem extends Item implements Usable, Inspectable {
    public TestItem(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        // In this test, we'll just publish an event indicating the item was used.
        publisher.publish(new UseItemEvent(this.getId(), this.getName(), "Nothing dramatic happens, but it's used."));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), "An intriguing test item."));
    }
}

