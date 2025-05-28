package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomWithPresetItems;

public class RoomInventoryProvider {
    public Inventory getInventoryFor(Room room) {
        // For example, you could check room type or zone.
        if (room instanceof RoomWithPresetItems) {
            // Return a preconfigured inventory with specific items.
            return new PresetInventory();
        } else {
            // For most rooms, return a randomly generated inventory or an empty one.
            return new BasicInventory();
        }
    }
}

