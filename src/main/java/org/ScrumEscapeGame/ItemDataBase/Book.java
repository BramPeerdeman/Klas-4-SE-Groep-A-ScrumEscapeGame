package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Rarity;
import org.ScrumEscapeGame.Items.Usable;

import java.util.List;

public class Book extends AbstractScroll {
    private final String subject;

    public Book(int id, String name, String description, String subject, List<String> texts, Rarity rarity) {
        super(id, name, description, texts, rarity);
        this.subject = subject;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public boolean isStackable() {
        // Decide whether books can be stacked. Adjust according to your game logic.
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        String uniqueContent = ContentCache.getUniqueText(1, subject, rarity, texts);
        publisher.publish(new UseItemEvent(this.id, this.name, "You used your book on " + subject));
        publisher.publish(new NotificationEvent("You skimmed the booklet about " + subject + ", the most important takeaway was: " + uniqueContent));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(this.id, this.name, "A fancy book about " + subject));
    }
}
