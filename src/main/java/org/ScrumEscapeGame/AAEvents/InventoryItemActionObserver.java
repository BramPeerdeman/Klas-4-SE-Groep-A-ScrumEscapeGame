package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Monster;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.*;
import org.ScrumEscapeGame.Rooms.BossLockedDoorConnection;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Weapons.Excalibur;
import org.ScrumEscapeGame.Weapons.Katana;
import org.ScrumEscapeGame.Weapons.StraightSword;
import org.ScrumEscapeGame.Weapons.Weapon;

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

                // If the item is a Key, follow the key logic for boss door unlocking.
                if (item instanceof Key) {
                    Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
                    Optional<BossLockedDoorConnection> bossConnOpt = currentRoom.getNeighbours().values().stream()
                            .filter(conn -> conn instanceof BossLockedDoorConnection)
                            .map(conn -> (BossLockedDoorConnection) conn)
                            .findFirst();
                    if (bossConnOpt.isPresent()) {
                        int requiredKeys = 6; // Example: keys required for a boss door.
                        boolean consumed = Key.tryConsumeKeysForUnlock(context.getPlayer(), requiredKeys);
                        if (consumed) {
                            BossLockedDoorConnection bossConn = bossConnOpt.get();
                            bossConn.getDoor().unlock();
                            context.getEventPublisher().publish(new DoorUnlockedEvent(bossConn.getDoor()));
                            uiService.printMessage("Boss door unlocked using your keys!");
                        } else {
                            uiService.printMessage("You do not have enough keys to unlock the boss door.");
                        }
                    } else {
                        uiService.printMessage("You are not near a boss door. Nothing happens.");
                    }

                } else {
                    // For non-key items
                    if (item instanceof Weapon) {
                        // Check if there is an active monster in the current room.
                        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
                        if (currentRoom instanceof RoomWithQuestion) {
                            RoomWithQuestion rq = (RoomWithQuestion) currentRoom;
                            if (rq.hasActiveMonster()) {
                                // Attack the active monster.
                                Weapon weapon = (Weapon) item;
                                Monster monster = rq.getActiveMonster();
                                attackMonster(context.getPlayer(), weapon, monster, context.getEventPublisher());
                            } else {
                                uiService.printMessage("There is no monster to attack here.");
                                return;  // Do not remove or use the weapon.
                            }
                        } else {
                            uiService.printMessage("There is no target for this weapon here.");
                            return;
                        }
                    } else if (item instanceof Usable) {
                        // Use any other usable item (like scrolls, potions, etc.)
                        ((Usable) item).use(context.getPlayer(), context.getEventPublisher());
                    }

                    // Decrement quantity for stackable items, or remove non-stackable items.
                    if (item instanceof StackableItem) {
                        StackableItem stackItem = (StackableItem) item;
                        stackItem.decrementQuantity(); // Decrements quantity by one.
                        if (stackItem.getQuantity() <= 0) {
                            context.getPlayer().getInventory().removeItem(item);
                        }
                    } else {
                        context.getPlayer().getInventory().removeItem(item);
                    }
                    uiService.printMessage("Used: " + item.getName());
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

    /**
     * Helper method to process a weapon attack.
     * The weapon deals damage to the monster, durability is reduced, and notifications are published.
     */
    private void attackMonster(Player player, Weapon weapon, Monster monster, EventPublisher<GameEvent> publisher) {
        int damage = weapon.attack();
        monster.takeDamage(damage);
        weapon.takeDurabilityDamage(1); // Each attack reduces durability by 1.
        publisher.publish(new NotificationEvent("You attacked " + monster.getName() +
                " with " + weapon.getName() + " for " + damage + " damage!"));

        // Check if the weapon's durability is depleted.
        if (weapon instanceof Excalibur || weapon instanceof Katana || weapon instanceof StraightSword) {
            // Here we assume each weapon has a getDurability() method.
            int remainingDurability = ((Weapon)weapon).getDurability();
            if (remainingDurability <= 0) {
                publisher.publish(new NotificationEvent(weapon.getName() + " has broken!"));
                context.getPlayer().getInventory().removeItem((Item) weapon);
            }
        }

        // If the monster's health is 0 or less, mark it as defeated.
        if (!monster.isAlive()) {
            publisher.publish(new NotificationEvent(monster.getName() + " is defeated!"));
            // Drop a Hint Joker into the current room's inventory.
            Room currentRoom = context.getRoomManager().getRoom(player.getPosition());
            if (currentRoom instanceof RoomWithQuestion) {
                // Create a new Hint Joker.
                JokerHint hintJoker = new JokerHint(105, "HintJoker", "Gotten from a slain monster. Provides a funny hint for this challenge.");
                // Add it to the room's inventory.
                currentRoom.getInventory().addItem(hintJoker);
                publisher.publish(new NotificationEvent("The fallen " + monster.getName() +
                        " dropped a Hint Joker!"));

                RoomWithQuestion rq = (RoomWithQuestion) currentRoom;
                rq.setActiveMonster(null);
            }
        }
    }

}

