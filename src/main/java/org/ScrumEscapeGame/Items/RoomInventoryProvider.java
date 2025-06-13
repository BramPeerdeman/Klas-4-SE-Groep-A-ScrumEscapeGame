package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomDefinition;
import org.ScrumEscapeGame.Rooms.RoomWithPresetItems;
import org.ScrumEscapeGame.Rooms.BossRoom;
import org.ScrumEscapeGame.Rooms.PenultimateRoom;
// Vergeet niet KeyJoker en JokerHint te importeren:
import org.ScrumEscapeGame.Items.JokerKey;
import org.ScrumEscapeGame.Items.JokerHint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoomInventoryProvider {

    // Candidate items categorized by rarity.
    private final List<Item> commonItems;
    private final List<Item> uncommonItems;
    private final List<Item> rareItems;
    // We will assume that unique items (e.g. the keys used to open the boss room)
    // are handled separately in the dedicated branches.

    public RoomInventoryProvider() {
        commonItems = new ArrayList<>();
        uncommonItems = new ArrayList<>();
        rareItems = new ArrayList<>();

        // Populate the common items list.
        commonItems.add(new TestItem(101, "Key", "A rusty key - A standard key"));
        commonItems.add(new TestItem(102, "Potion", "A healing potion - A small healing potion"));
        commonItems.add(new TestItem(103, "Coin", "A gold coin - A shiny gold coin"));

        // Populate the uncommon items list.
        uncommonItems.add(new TestItem(107, "Apple", "A fresh apple to restore energy"));
        uncommonItems.add(new TestItem(108, "Notebook", "A small notebook containing Scrum jargon"));

        // Populate the rare items list.
        rareItems.add(new TestItem(109, "Magic Wand", "A wand said to boost your Scrum insights"));
        rareItems.add(new TestItem(110, "Ancient Scroll", "An old scroll explaining Scrum history"));

        // If needed, you can add more items here as your project evolves.
    }

    public Inventory getInventoryFor(Room room) {
        String roomName;
        RoomDefinition def = getDefinitionFor(room);
        if (def != null) {
            roomName = def.getType();
        } else {
            roomName = ""; // Fallback, or you can decide on a default type.
        }

        Inventory inventory;

        // For preset rooms we follow existing logic.
        if (room instanceof RoomWithPresetItems) {
            PresetInventory preset = new PresetInventory();
            Random rnd = new Random(room.getId());
            int variant = rnd.nextInt(3);
            switch (variant) {
                case 0:
                    preset.addItem(new TestItem(201, "Special Key", "… Variant 0 …"));
                    preset.addItem(new TestItem(202, "Ancient Scroll", "… Variant 0 …"));
                    break;
                case 1:
                    preset.addItem(new TestItem(203, "Magic Wand", "… Variant 1 …"));
                    preset.addItem(new TestItem(204, "Old Map", "… Variant 1 …"));
                    break;
                default:
                    preset.addItem(new TestItem(205, "Enchanted Cup", "… Variant 2 …"));
                    preset.addItem(new TestItem(206, "Mystic Ring", "… Variant 2 …"));
                    break;
            }
            inventory = preset;
        }
        else if (room instanceof BossRoom) {
            // For BossRoom and PenultimateRoom, we use unique items.
            // Note: Changing each Key to be unique (non-stackable) might require extra refactoring.
            // For now, we assume they are non-stackable or handled in PresetInventory.
            PresetInventory bossInv = new PresetInventory();
            bossInv.addItem(new Key(300, "Boss Key", "Een sleutel voor de baasdeur.", 6));
            inventory = bossInv;
        }
        else if (room instanceof PenultimateRoom) {
            PresetInventory penInv = new PresetInventory();
            penInv.addItem(new Key(400, "Penultimate Key", "Een sleutel uit de voorlaatste kamer.", 6));
            inventory = penInv;
        }
        else {
            // For generic rooms, we generate a basic inventory using our candidate items from our rarity buckets.
            BasicInventory basic = new BasicInventory();
            Random rnd = new Random(room.getId());
            // Decide on how many items to add (for example, 1 to 3 items)
            int count = rnd.nextInt(3) + 1;
            for (int i = 0; i < count; i++) {
                double weight = rnd.nextDouble();
                // Use weighted probabilities for rarity:
                // 70% chance for a common item, 20% for uncommon, 10% for rare.
                if (weight < 0.7 && !commonItems.isEmpty()) {
                    basic.addItem(commonItems.get(rnd.nextInt(commonItems.size())));
                } else if (weight < 0.9 && !uncommonItems.isEmpty()) {
                    basic.addItem(uncommonItems.get(rnd.nextInt(uncommonItems.size())));
                } else if (!rareItems.isEmpty()) {
                    basic.addItem(rareItems.get(rnd.nextInt(rareItems.size())));
                }
            }
            inventory = basic;
        }

        // For specific room names, add Joker items (note these are bonus/informative items).
        if (def != null) {
            String roomType = def.getType();
            // Optionally, you could also check def.getAllowedJokers() and add only if they’re allowed.
            if ("ProductBacklog".equals(roomType)) {
                inventory.addItem(new JokerKey(104, "KeyJoker", "Geeft een extra sleutel in Product Backlog."));
                inventory.addItem(new JokerHint(105, "HintJoker", "Geeft een hint bij de Product Backlog-vraag."));
            }
            else if ("Review".equals(roomType) || "SprintReview".equals(roomType)) {
                inventory.addItem(new JokerKey(201, "KeyJoker", "Geeft een extra sleutel in Review."));
                inventory.addItem(new JokerHint(105, "HintJoker", "Geeft een hint bij de SprintReview-vraag."));
            }
        }


        return inventory;
    }

    private RoomDefinition getDefinitionFor(Room room) {
        return RoomDefinition.sampleDefinitions().stream()
                .filter(def -> def.getId() == room.getId())
                .findFirst()
                .orElse(null);
    }

}
