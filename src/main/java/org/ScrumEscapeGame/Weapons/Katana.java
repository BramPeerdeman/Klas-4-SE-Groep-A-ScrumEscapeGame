package org.ScrumEscapeGame.Weapons;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.ItemInspectEvent;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.Usable;

public class Katana extends Item implements Weapon, Inspectable, Usable {
    private int durability;
    private final int damage;

    public Katana(int id, String description ,int durability, int damage) {
        super(id, "Katana", description);
        this.durability = 1;
        this.damage = 3;
    }

    @Override
    public int attack() {
        return damage;
    }

    @Override
    public void takeDurabilityDamage(int durabilityDamage) {
        durability -= durabilityDamage;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), getDescription()));
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        return false;
    }

    @Override
    public boolean isStackable() {
        return false;
    }
}
