package org.ScrumEscapeGame.Weapons;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.ItemInspectEvent;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.Usable;

public class Excalibur extends Item implements Weapon, Inspectable, Usable {
    private int durability;
    private final int damage;

    public Excalibur(int id, String description ,int durability, int damage) {
        super(id, "Excalibur", description);
        this.durability = 2;
        this.damage = 1;
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
