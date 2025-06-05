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

    private final List<Item> candidateItems;

    public RoomInventoryProvider() {
        candidateItems = new ArrayList<>();
        candidateItems.add(new TestItem(101, "Key",   "A rusty key - A standard key"));
        candidateItems.add(new TestItem(102, "Potion","A healing potion - A small healing potion"));
        candidateItems.add(new TestItem(103, "Coin",  "A gold coin - A shiny gold coin"));
        // … evt. meer TestItems …
    }

    public Inventory getInventoryFor(Room room) {
        String roomName = RoomDefinition.getType();
        Inventory inventory;

        if (room instanceof RoomWithPresetItems) {
            PresetInventory preset = new PresetInventory();
            Random rnd = new Random(room.getId());
            int variant = rnd.nextInt(3);
            switch (variant) {
                case 0:
                    preset.addItem(new TestItem(201, "Special Key",   "… Variant 0 …"));
                    preset.addItem(new TestItem(202, "Ancient Scroll","… Variant 0 …"));
                    break;
                case 1:
                    preset.addItem(new TestItem(203, "Magic Wand",    "… Variant 1 …"));
                    preset.addItem(new TestItem(204, "Old Map",       "… Variant 1 …"));
                    break;
                default:
                    preset.addItem(new TestItem(205, "Enchanted Cup", "… Variant 2 …"));
                    preset.addItem(new TestItem(206, "Mystic Ring",   "… Variant 2 …"));
                    break;
            }
            inventory = preset;
        }
        else if (room instanceof BossRoom) {
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
            BasicInventory basic = new BasicInventory();
            Random rnd = new Random(room.getId());
            int count = rnd.nextInt(candidateItems.size() + 1);
            List<Item> itemsCopy = new ArrayList<>(candidateItems);
            Collections.shuffle(itemsCopy, rnd);
            for (int i = 0; i < count; i++) {
                basic.addItem(itemsCopy.get(i));
            }
            inventory = basic;
        }

        // **Hier komt de correcte check op kamernaam**:
        // In plaats van RoomDefinition.getType(), gebruik je room.getDefinition().getType()
        if ("ProductBacklog".equals(roomName)) {
            // Zorg dat je KeyJoker en JokerHint wél hebt geïmporteerd!
            inventory.addItem(new JokerKey(104, "KeyJoker",  "Geeft een extra sleutel in Product Backlog."));
            inventory.addItem(new JokerHint(105, "HintJoker","Geeft een hint bij de Product Backlog-vraag."));
        }
        else if ("Review".equals(roomName) || "SprintReview".equals(roomName)) {
            inventory.addItem(new JokerKey(201, "KeyJoker", "Geeft een extra sleutel in Review."));
        }

        return inventory;
    }
}
