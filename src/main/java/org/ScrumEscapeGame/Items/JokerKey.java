package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.ItemInspectEvent;
import org.ScrumEscapeGame.AAEvents.UseItemEvent;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Player;

public class JokerKey extends Item implements Usable, Inspectable {
    private static final int GENERATED_KEY_ID = 999;
    public JokerKey(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        // 1) Maak een nieuw KeyItem aan (of verkrijg een bestaand key-object)
        Key newKey = new Key(
                GENERATED_KEY_ID,
                "Sleutel",
                "Een sleutel verkregen via KeyJoker",
                1
        );

        // 2) Voeg de sleutel toe aan de inventaris van de speler
        Inventory inv = player.getInventory();
        inv.addItem(newKey);

        // 3) Publiceer een UseItemEvent om het gebruik te melden
        publisher.publish(new UseItemEvent(
                this.getId(),
                this.getName(),
                "Je hebt een extra sleutel ontvangen en in je inventory geplaatst!"
        ));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), getDescription()));
        new ItemInspectEvent(
                this.getId(),
                this.getName(),
                this.getDescription()
        );


    }
}
