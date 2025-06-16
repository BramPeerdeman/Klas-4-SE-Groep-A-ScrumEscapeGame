package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Room;

import java.util.Random;

public class JokerItemFactory implements ItemFactory {
    private final Random random = new Random();

    @Override
    public Item createItem(Room room, Rarity rarity) {
        // Chance (in the range 0.0–1.0) that joker spawns.
        double spawnChance = 0.99;
        if (spawnChance > random.nextDouble()) {
            // For rooms like "Daily Scrum" or "Review", prefer a Key Joker.
            // (The original canKeyBeUsed logic exists within the JokerKey class.)
            if ("Daily Scrum".equals(room.getName()) || "Review".equals(room.getName())) {
                return new JokerKey(104, "KeyJoker", "Provides an extra key in " + room.getName() + ".");
            } else {
                // In every other room, a Hint Joker is more suitable.
                return new JokerHint(105, "HintJoker", "Provides a funny hint for room " + room.getName() + ".");
            }
        }
        return new Key(777, "Lucky Key", "An ordinary looking key, you don't know how" +
                " but you have the feeling that it brings fortune.", 1);
    }
}


