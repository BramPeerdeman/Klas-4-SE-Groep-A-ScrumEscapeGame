package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.Items.Rarity;

import java.util.*;

public class ContentCache {
    // Key structure: "playerId|subject|rarity"
    private static final Map<String, Set<Integer>> cache = new HashMap<>();
    private static final Random random = new Random();

    /**
     * Given a list of texts, returns one that hasn't been seen before by the player,
     * if available. Otherwise, resets and returns one at random.
     */
    public static String getUniqueText(int playerId, String subject, Rarity rarity, List<String> texts) {
        String key = playerId + "|" + subject + "|" + rarity.name();
        Set<Integer> usedIndices = cache.computeIfAbsent(key, k -> new HashSet<>());

        // Gather available indices.
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            if (!usedIndices.contains(i)) {
                available.add(i);
            }
        }

        int selected;
        if (available.isEmpty()) {
            // Reset if all texts have been used.
            usedIndices.clear();
            selected = random.nextInt(texts.size());
        } else {
            selected = available.get(random.nextInt(available.size()));
        }

        // Mark as used.
        usedIndices.add(selected);
        return texts.get(selected);
    }
}

