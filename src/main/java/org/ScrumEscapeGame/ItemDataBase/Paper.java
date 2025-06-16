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

public class Paper implements Scrolls, Usable, Inspectable {
    private final int id;
    private final String subject;
    private final List<String> texts;
    private final Rarity rarity;
    private final String name;


    public Paper(String subject, List<String> texts, Rarity rarity, int id) {
        this.subject = subject;
        this.texts = texts;
        this.rarity = rarity;
        this.id = id;
        this.name = "Paper of " + getSubject();
    }


    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public List<String> getText() {
        return texts;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        // In this test, we'll just publish an event indicating the item was used.
        publisher.publish(new UseItemEvent(this.id, this.name, "You used your paper"));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(this.id, this.name, "A small piece of paper about "+ getSubject()));
    }
}