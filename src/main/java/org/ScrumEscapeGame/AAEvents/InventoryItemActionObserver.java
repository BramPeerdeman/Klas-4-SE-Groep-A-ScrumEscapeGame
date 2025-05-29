package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.Usable;

public class InventoryItemActionObserver implements EventObserver<GameEvent> {
    private final GameContext context;
    private final GameUIService uiService;

    public InventoryItemActionObserver(GameContext context, GameUIService uiService) {
        this.context = context;
        this.uiService = uiService;
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof InventoryItemActionEvent) {
            InventoryItemActionEvent actionEvent = (InventoryItemActionEvent) event;
            String action = actionEvent.getAction();
            String source = actionEvent.getSource();
            Item item = findItemById(actionEvent.getItemId(), source);
            if (item == null) {
                uiService.printMessage("Error: Item not found for action.");
                return;
            }

            if (action.equalsIgnoreCase("pickup") && source.equalsIgnoreCase("room")) {
                // Logic for picking up: transfer item from room inventory to player inventory.
                Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
                boolean transferred = context.getInventoryManager().transferItem(
                        item, currentRoom.getInventory(), context.getPlayer().getInventory(),
                        context.getEventPublisher());

                if (transferred) {
                    System.out.println("DEBUG: You picked up an item.");
                    uiService.printMessage("Picked up: " + item.getName());
                } else {
                    uiService.printMessage("Failed to pick up: " + item.getName());
                    System.out.println("DEBUG: You DIDN'T picked up an item.");
                }

            } else if (action.equalsIgnoreCase("use") && source.equalsIgnoreCase("player")) {
                // Logic for using the item.
                if (item instanceof Usable) {
                    ((Usable) item).use(context.getPlayer(), context.getEventPublisher());
                    // Optionally remove the item after use:
                    context.getPlayer().getInventory().removeItem(item);
                    uiService.printMessage("Used: " + item.getName());
                } else {
                    uiService.printMessage("Item cannot be used: " + item.getName());
                }

            } else if (action.equalsIgnoreCase("inspect")) {
                if (item instanceof Inspectable) {
                    ((Inspectable) item).inspect(context.getPlayer(), context.getEventPublisher());
                } else {
                    uiService.printMessage("Item cannot be inspected: " + item.getName());
                }
            }
            uiService.refreshInventory();
        }
    }

    private Item findItemById(int itemId, String source) {
        if (source.equalsIgnoreCase("room")) {
            Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
            for (Item i : currentRoom.getInventory().getItems()) {
                if (i.getId() == itemId) return i;
            }
        } else if (source.equalsIgnoreCase("player")) {
            for (Item i : context.getPlayer().getInventory().getItems()) {
                if (i.getId() == itemId) return i;
            }
        }
        return null;
    }
}

