package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Item;  // zorg dat je Item importeert

public class PickUpCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;
    private final String targetName;

    public PickUpCommand(GameContext context, EventPublisher<GameEvent> publisher, String targetName) {
        this.context = context;
        this.publisher = publisher;
        this.targetName = targetName;
    }

    @Override
    public void execute() {
        // Get the current room’s inventory using the room inventory provider.
        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
        Inventory roomInv = currentRoom.getInventory();

        // Find the item in the room's inventory.
        Item found = roomInv.getItems().stream()
                .filter(i -> i.getName().equalsIgnoreCase(targetName))
                .findFirst()
                .orElse(null);

        if (found != null) {
            // Use the InventoryManager to transfer the item.
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

