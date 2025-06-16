package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.Items.Rarity;
import java.util.*;

import java.util.*;

public class Database {
    private static final Map<String, List<String>> data = new HashMap<>();

    static {
        // Room type–specific entries:

        // BacklogRefinement scrolls:
        data.put("BacklogRefinement|COMMON", List.of(
                "The product backlog is a dynamic list of work items maintained by the Product Owner.",
                "Backlog refinement helps ensure the top items are ready for upcoming sprints."
        ));
        data.put("BacklogRefinement|UNCOMMON", List.of(
                "Intermediate techniques for prioritizing and refining the backlog."
        ));
        data.put("BacklogRefinement|RARE", List.of(
                "Advanced theories on backlog optimization and dynamic prioritization."
        ));

        // Planning scrolls:
        data.put("Planning|COMMON", List.of(
                "Simple planning strategies and meeting essentials."
        ));
        data.put("Planning|UNCOMMON", List.of(
                "Intermediate planning insights for resource and time management."
        ));
        data.put("Planning|RARE", List.of(
                "Expert-level planning methodologies that integrate risk management with agile practices."
        ));

        // SprintBacklog scrolls:
        data.put("SprintBacklog|COMMON", List.of(
                "A straightforward rundown of sprint task lists."
        ));
        data.put("SprintBacklog|UNCOMMON", List.of(
                "Techniques for maintaining and optimizing the sprint backlog."
        ));
        data.put("SprintBacklog|RARE", List.of(
                "Deep theoretical insights into maximizing sprint throughput and backlog efficiency."
        ));

        // SprintReview scrolls:
        data.put("SprintReview|COMMON", List.of(
                "Fundamental guidelines for conducting a sprint review."
        ));
        data.put("SprintReview|UNCOMMON", List.of(
                "Intermediate strategies for gathering useful feedback during sprint reviews."
        ));
        data.put("SprintReview|RARE", List.of(
                "Expert advice on leveraging sprint reviews to capture transformative insights."
        ));

        // Penultimate scrolls:
        data.put("Penultimate|COMMON", List.of(
                "A brief glimpse into the challenges of the penultimate room."
        ));
        data.put("Penultimate|UNCOMMON", List.of(
                "Subtle hints that prepare you for tougher challenges ahead."
        ));
        data.put("Penultimate|RARE", List.of(
                "Advanced strategies for overcoming the penultimate test."
        ));

        // Boss scrolls:
        data.put("Boss|COMMON", List.of(
                "Basic strategic pointers for the final confrontation."
        ));
        data.put("Boss|UNCOMMON", List.of(
                "Intermediate tactics and insights for facing the boss."
        ));
        data.put("Boss|RARE", List.of(
                "Exclusive lore and advanced strategies revealing the secrets of defeating the boss."
        ));

        // Default list for general Scrum definitions:
        data.put("Default|COMMON", List.of(
                "Scrum is an agile framework focused on iterative progress.",
                "It emphasizes collaboration, accountability, and continuous improvement."
        ));
        data.put("Default|UNCOMMON", List.of(
                "Scrum implementations vary, but common elements include daily stand-ups and sprints.",
                "A good Scrum team understands the importance of self-organization and adapts quickly."
        ));
        data.put("Default|RARE", List.of(
                "Advanced Scrum practices involve deep dives into agile philosophy and lean methodologies.",
                "Mastery of Scrum means tailoring its principles as projects evolve, unlocking hidden efficiencies."
        ));
    }

    /**
     * Fetches texts based on a given subject (typically a room type) and a rarity level.
     *
     * The method first attempts to find an entry matching the subject and rarity.
     * If not found, it will fall back to the default Scrum definitions.
     *
     * @param subject The room type or subject of the scroll.
     * @param rarity  The rarity level (COMMON, UNCOMMON, RARE).
     * @return A list of texts associated with the subject and rarity.
     */
    public static List<String> getTexts(String subject, Rarity rarity) {
        // Attempt to retrieve scrolls for the specific subject.
        List<String> texts = data.get(subject + "|" + rarity.name());
        // Fallback to default general Scrum definitions if no specific texts are found.
        if (texts == null || texts.isEmpty()) {
            texts = data.get("Default|" + rarity.name());
        }
        return texts;
    }
}

