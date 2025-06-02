package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.BossRoom;
import org.ScrumEscapeGame.Rooms.PenultimateRoom;
import org.ScrumEscapeGame.Rooms.RoomWithPresetItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoomInventoryProvider {

    // A pool of candidate items to add to basic room inventories.
    private final List<Item> candidateItems;

    public RoomInventoryProvider() {
        candidateItems = new ArrayList<>();
        // For testing purposes, we add a few items.
        candidateItems.add(new TestItem(101, "Key", "A rusty key - A standard key"));
        candidateItems.add(new TestItem(102, "Potion", "A healing potion - A small healing potion"));
        candidateItems.add(new TestItem(103, "Coin", "A gold coin - A shiny gold coin"));
        // Add more candidate items as needed…
    }

    /**
     * Given a room, returns an inventory appropriate to its type.
     * For preset rooms, a PresetInventory is created and preconfigured with
     * hardcoded items. For other rooms, a BasicInventory is generated and a random
     * selection of candidate items is added.
     *
     * @param room the room for which to generate an inventory.
     * @return an Inventory instance populated with items.
     */
    public Inventory getInventoryFor(Room room) {
        if (room instanceof RoomWithPresetItems) {
            // For preset rooms, create a PresetInventory and add a variant set of items.
            PresetInventory preset = new PresetInventory();
            // Use the room's ID (or another attribute) to seed a variant selection.
            Random rnd = new Random(room.getId());
            int variant = rnd.nextInt(3);  // Suppose we have 3 distinct preset configurations.

            switch (variant) {
                case 0:
                    preset.addItem(new TestItem(201, "Special Key", "A key unique to this room - Variant 0: Opens a secret door."));
                    preset.addItem(new TestItem(202, "Ancient Scroll", "An old scroll with cryptic symbols - Variant 0: Might give clues if deciphered."));
                    break;
                case 1:
                    preset.addItem(new TestItem(203, "Magic Wand", "A wand with mysterious powers - Variant 1: Glows in the dark."));
                    preset.addItem(new TestItem(204, "Old Map", "A faded map - Variant 1: Reveals hidden passageways."));
                    break;
                case 2:
                    preset.addItem(new TestItem(205, "Enchanted Cup", "A cup that shimmers - Variant 2: Never seems to empty."));
                    preset.addItem(new TestItem(206, "Mystic Ring", "A ring with a soft glow - Variant 2: Said to bestow luck."));
                    break;
                default:
                    // Fallback preset; should not occur.
                    preset.addItem(new TestItem(201, "Special Key", "A default special key."));
                    break;
            }
            return preset;
        } else if (room instanceof BossRoom) {
            PresetInventory preset = new PresetInventory();
            // For stackable keys, a single Key item with quantity = 6 could suffice.
            preset.addItem(new Key(300, "Boss Key", "A mighty key for unlocking the boss door.", 6));
            return preset;
        } else if (room instanceof PenultimateRoom) {
            PresetInventory preset = new PresetInventory();
            preset.addItem(new Key(400, "Penultimate Key", "A key from the penultimate chamber.", 6));
            return preset;
        } else {
            // For generic rooms, generate a BasicInventory and add a random set of items.
            BasicInventory basic = new BasicInventory();
            Random rnd = new Random();
            // Determine how many items to add. For example, choose a number between 0 and the candidate pool size.
            int count = rnd.nextInt(candidateItems.size() + 1);

            // Shuffle the candidate items to ensure randomness.
            List<Item> itemsCopy = new ArrayList<>(candidateItems);
            Collections.shuffle(itemsCopy, rnd);

            for (int i = 0; i < count; i++) {
                basic.addItem(itemsCopy.get(i));
            }
            return basic;
        }
    }
}


