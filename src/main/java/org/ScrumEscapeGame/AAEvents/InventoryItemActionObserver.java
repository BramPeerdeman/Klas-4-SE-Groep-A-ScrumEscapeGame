package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.*;
import org.ScrumEscapeGame.Rooms.BossLockedDoorConnection;

import java.util.Optional;

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
                // Handle the "use" command for items in the player's inventory.
                if (item instanceof Usable) {
                    // Check if the item is a Key.
                    if (item instanceof Key) {
                        // First, check if the player is near a boss door.
                        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
                        Optional<BossLockedDoorConnection> bossConnOpt = currentRoom.getNeighbours().values().stream()
                                .filter(conn -> conn instanceof BossLockedDoorConnection)
                                .map(conn -> (BossLockedDoorConnection) conn)
                                .findFirst();
                        if (bossConnOpt.isPresent()) {
                            // The player is adjacent to the boss door.
                            // Attempt to consume the required keys—say, 6 keys.
                            int requiredKeys = 6; // This could be parameterized for different boss rooms.
                            boolean consumed = Key.tryConsumeKeysForUnlock(context.getPlayer(), requiredKeys);
                            if (consumed) {
                                // Unlock the door.
                                BossLockedDoorConnection bossConn = bossConnOpt.get();
                                bossConn.getDoor().unlock();
                                context.getEventPublisher().publish(new DoorUnlockedEvent(bossConn.getDoor()));
                                uiService.printMessage("Boss door unlocked using your keys!");
                            } else {
                                uiService.printMessage("You do not have enough keys to unlock the boss door.");
                            }
                        } else {
                            // The player is not near a boss door.
                            uiService.printMessage("You are not near a boss door. Nothing happens.");
                        }
                    } else {
                        // For non-key items, use them normally.
                        ((Usable) item).use(context.getPlayer(), context.getEventPublisher());
                        // Remove non-stackable items outright, or for stackable items remove only if quantity is 0.
                        if (item instanceof StackableItem) {
                            StackableItem stackItem = (StackableItem) item;
                            if (stackItem.getQuantity() <= 0) {
                                context.getPlayer().getInventory().removeItem(item);
                            }
                        } else {
                            context.getPlayer().getInventory().removeItem(item);
                        }
                        uiService.printMessage("Used: " + item.getName());
                    }
                } else {
                    uiService.printMessage("Item cannot be used: " + item.getName());
                }
                uiService.refreshInventory();
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

