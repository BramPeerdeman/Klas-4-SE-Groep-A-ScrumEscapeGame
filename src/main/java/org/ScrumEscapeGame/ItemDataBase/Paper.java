package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Rarity;
import org.ScrumEscapeGame.Items.Usable;

import java.util.List;

public class Paper extends AbstractScroll{

    private final String subject;

    public Paper(int id, String name, String description, String subject, List<String> texts, Rarity rarity) {
        super(id, name, description, texts, rarity);
        this.subject = subject;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public boolean isStackable() {
        // For example, papers might be stackable if they’re considered common or drop items.
        return true;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        String uniqueContent = ContentCache.getUniqueText(1, subject, rarity, texts);
        publisher.publish(new UseItemEvent(this.id, this.name, "You used your paper."));
        publisher.publish(new NotificationEvent("The paper reads: " + uniqueContent));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(this.id, this.name, "A small piece of paper about " + subject));
    }
}
