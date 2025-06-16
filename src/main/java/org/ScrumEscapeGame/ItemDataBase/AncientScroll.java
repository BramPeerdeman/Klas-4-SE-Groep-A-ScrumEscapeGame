package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.ItemInspectEvent;
import org.ScrumEscapeGame.AAEvents.UseItemEvent;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Rarity;
import org.ScrumEscapeGame.Items.Usable;

import java.util.List;

public class AncientScroll extends AbstractScroll{
    private final String subject;

    public AncientScroll(int id, String name, String description, String subject, List<String> texts, Rarity rarity) {
        super(id, name, description, texts, rarity);
        this.subject = subject;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new UseItemEvent(this.id, this.name, "You used the ancient scroll on " + subject));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(this.id, this.name, "An ancient scroll about " + subject));
    }
}

