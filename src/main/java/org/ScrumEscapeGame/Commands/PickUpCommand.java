package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Item;  // zorg dat je Item importeert

import java.util.List;

public class PickUpCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;

    public PickUpCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute(String args) {
        String argument = args.trim();
        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
        Inventory roomInv = currentRoom.getInventory();
        Item found = null;
        List<Item> items = roomInv.getItems();

        // Attempt to treat the argument as an index.
        try {
            int index = Integer.parseInt(argument) - 1; // assuming user inputs 1-based index
            if (index >= 0 && index < items.size()) {
                found = items.get(index);
            } else {
                publisher.publish(new NotificationEvent("Index out of bounds in room inventory."));
                return;
            }
        } catch (NumberFormatException nfe) {
            // Fallback: use a name-based lookup
            found = items.stream()
                    .filter(i -> i.getName().equalsIgnoreCase(argument))
                    .findFirst().orElse(null);
        }

        if (found != null) {
            boolean transferred = context.getInventoryManager().transferItem(
                    found, roomInv, context.getPlayer().getInventory(), publisher
            );
            if (transferred) {
                publisher.publish(new NotificationEvent("Je hebt opgepakt: " + found.getName()));
            }
        } else {
            publisher.publish(new NotificationEvent("Dit item ligt hier niet."));
        }
    }
}


