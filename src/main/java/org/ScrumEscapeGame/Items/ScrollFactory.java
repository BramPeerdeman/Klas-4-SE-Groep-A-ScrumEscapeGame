package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.ItemDataBase.*;
import org.ScrumEscapeGame.Rooms.RoomDefinition;

import java.util.List;
import java.util.Random;

public class ScrollFactory implements ItemFactory {
    private final Random random = new Random();
    // Chance (in the range 0.0–1.0) that we use generic/default texts.
    private final double defaultChance;
    boolean premade = false;

    public ScrollFactory(double defaultChance) {
        this.defaultChance = defaultChance;
    }

    @Override
    public Item createItem(Room room, Rarity rarity) {
        int itemId = room.getId();
        // Retrieve the room definition. Assume we have a static helper.
        RoomDefinition def = RoomInventoryProvider.getDefinitionForStatic(room);
        // Decide randomly based on defaultChance whether to use the room-specific subject.
        String subject;
        if (def != null && random.nextDouble() > defaultChance || def != null && def.getType().equals("Penultimate")) {
            subject = def.getType();
        } else {
            subject = "Default";
            itemId += 2000;
        }

        if (def != null && def.getType().equals("Penultimate")){
            premade = true;
        }
        // Look up the texts using the Database
        List<String> texts = Database.getTexts(subject, rarity);
        // Use the cache to choose one that hasn't been seen for this player.
        // Assume you have access to the player's id; here we simulate with 1 as a placeholder.
        // Erm... this is a single player game!
        String selectedText = ContentCache.getUniqueText(1, subject, rarity, texts);
        // Vary the concrete type of scroll slightly
        double preRoll = random.nextDouble();
        if (preRoll < 0.5 && !premade) {
            double roll = random.nextDouble();
            if (roll < 0.33) {
                return new AncientScroll(itemId + 1100,
                        "Ancient Scroll of " + subject,
                        "An ancient scroll with lore on " + subject,
                        subject,
                        texts,
                        rarity);
            } else if (roll < 0.66) {
                return new Book(itemId + 1050,
                        "Book of " + subject,
                        "A book discussing " + subject,
                        subject,
                        texts,
                        rarity);
            } else {
                return new Paper(itemId + 1010,
                        "Paper of: " + subject,
                        "A small piece of paper with with a line about : " + subject,
                        subject,
                        texts,
                        rarity);
            }
        } else if (rarity.equals(Rarity.RARE)) {
            return new AncientScroll(itemId + 1100,
                    "Ancient Scroll of " + subject,
                    "An ancient scroll with lore on " + subject,
                    subject,
                    texts,
                    rarity);
        } else if (rarity.equals(Rarity.UNCOMMON)) {
            return new Book(itemId + 1050,
                    "Book of " + subject,
                    "A book discussing " + subject,
                    subject,
                    texts,
                    rarity);
        } else {
            return new Paper(itemId + 1010,
                    "Paper of: " + subject,
                    "A small piece of paper with with a line about : " + subject,
                    subject,
                    texts,
                    rarity);
        }
    }
}

