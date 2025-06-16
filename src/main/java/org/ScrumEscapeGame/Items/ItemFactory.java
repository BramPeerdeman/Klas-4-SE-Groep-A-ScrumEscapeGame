package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Room;

public interface ItemFactory {
    /**
     * Creates an item for the given room and rarity.
     */
    Item createItem(Room room, Rarity rarity);
}

