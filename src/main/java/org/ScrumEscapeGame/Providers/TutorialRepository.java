package org.ScrumEscapeGame.Providers;

import java.util.HashMap;
import java.util.Map;

public class TutorialRepository {

    // A map to store tutorial messages. The key can be the room display order or room ID.
    private static final Map<Integer, String> tutorials = new HashMap<>();

    // Initialize the repository with tutorial messages.
    static {
        tutorials.put(1,
                "\n--- Controls ---\n" +
                        "W - Move north        M - Open the level map\n" +
                        "A - Move west         L - Get the lore of the room\n" +
                        "S - Move south        K - Check the status of the game\n" +
                        "D - Move east         Q - Attempt the room's question\n" +
                        "SAVE - Type save to save the game\n" +
                        "I - Open/close your inventory (uses mouse controls)\n"
        );
        tutorials.put(2, "When your health reaches zero, the game resets.");
        tutorials.put(3, "Use your resources wisely! Every decision affects your progress.");
        tutorials.put(4, "Hints nudge you in the right direction, you have to deliberate before answering.");
        tutorials.put(5, "Keys are needed to open a locked door, the bossroom needs 6 'penultimate keys'");
        // Add more entries as needed.
    }

    /**
     * Returns a tutorial message based on the room's display order.
     *
     * @param displayOrder The display order of the current room.
     * @return the corresponding tutorial message, or a default message if none exists.
     */
    public static String getTutorialMessage(int displayOrder) {
        return tutorials.getOrDefault(displayOrder, "Keep exploring and applying agile principles!");
    }
}

