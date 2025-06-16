package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemSpawnManager {
    // A list of factories to use for spawning items.
    private final List<ItemFactory> factories = new ArrayList<>();
    private final Random random = new Random();

    public void registerFactory(ItemFactory factory) {
        factories.add(factory);
    }

    /**
     * Spawns a random item category based on the registered factories.
     * You can later modify this to use weighted probabilities.
     */
    public Item spawnRandomItem(Room room, Rarity rarity) {
        if (factories.isEmpty()) {
            throw new IllegalStateException("No factories registered!");
        }
        int idx = random.nextInt(factories.size());
        return factories.get(idx).createItem(room, rarity);
    }
}
