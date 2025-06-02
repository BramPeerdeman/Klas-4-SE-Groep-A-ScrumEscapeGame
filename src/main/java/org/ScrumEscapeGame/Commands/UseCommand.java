package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.Unlockable;
import org.ScrumEscapeGame.Items.Usable;

import java.util.List;


public class UseCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;

    public UseCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute(String args) {
        String argument = args.trim();
        Inventory inventory = context.getPlayer().getInventory();
        Item found = null;
        List<Item> items = inventory.getItems();

        try {
            int index = Integer.parseInt(argument) - 1; // assuming 1-based index from user input
            if (index >= 0 && index < items.size()){
                found = items.get(index);
            } else {
                publisher.publish(new NotificationEvent("Index out of bounds in your inventory."));
                return;
            }
        } catch (NumberFormatException e) {
            // Fallback: name-based search.
            found = items.stream()
                    .filter(i -> i.getName().equalsIgnoreCase(argument))
                    .findFirst().orElse(null);
        }

        if (found instanceof Usable usableItem) {
            boolean success = usableItem.use(context.getPlayer(), publisher);
            if (success) {
                // Optionally remove the item if applicable.
                inventory.removeItem(found);
            }
        } else {
            publisher.publish(new NotificationEvent("Dit item heeft geen bruikbare effect of is niet aanwezig."));
        }
    }
}



