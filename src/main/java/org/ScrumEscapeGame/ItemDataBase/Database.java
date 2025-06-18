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
                "Effective refinement includes breaking large items into smaller tasks and adding clear acceptance criteria.",
                "Refinement is a shared responsibility involving the whole Scrum Team, not just the Product Owner."
        ));
        data.put("BacklogRefinement|RARE", List.of(
                "Backlog refinement sessions should balance the cost of time spent versus the clarity gained for development.",

                "Advanced teams use refinement to uncover dependencies and anticipate blockers several sprints ahead."
        ));

        // Planning scrolls:
        data.put("Planning|COMMON", List.of(
                "Sprint Planning kicks off each sprint and sets the stage for what will be built.",

                "The team collaborates to determine what can be delivered and how it will be achieved."
        ));
        data.put("Planning|UNCOMMON", List.of(
                "Capacity planning considers team availability, velocity, and historical data to improve accuracy.",

                "A strong Sprint Goal acts as a guiding north star during planning and execution."
        ));
        data.put("Planning|RARE", List.of(
                "Strategic Sprint Planning includes aligning with quarterly objectives and integrating stakeholder roadmaps.",

                "Expert planners visualize work in systems thinking models to prevent localized optimization."
        ));

        // SprintBacklog scrolls:
        data.put("SprintBacklog|COMMON", List.of(
                "The Sprint Backlog is a subset of the Product Backlog selected for the sprint.",

                "It includes the sprint goal, selected backlog items, and a plan for delivering them."

        ));
        data.put("SprintBacklog|UNCOMMON", List.of(
                "Daily updates to the Sprint Backlog during stand-ups reflect progress and adjust for new learnings.",

                "Tasking out backlog items into smaller actionable steps can clarify scope and uncover hidden work."
        ));
        data.put("SprintBacklog|RARE", List.of(
                "Veteran Scrum Teams use the Sprint Backlog as a dynamic information radiator, continuously shaped during the sprint.",

                "Metrics like work item age and throughput can optimize Sprint Backlog management."
        ));

        // SprintReview scrolls:
        data.put("SprintReview|COMMON", List.of(
                "Sprint Review is a collaborative session to inspect the outcome of the sprint.",

                "Stakeholders are invited to provide feedback on what was done."
        ));
        data.put("SprintReview|UNCOMMON", List.of(
                "Effective reviews frame feedback in the context of the Sprint Goal and product roadmap.",

                "Demos should reflect not just working software, but user value delivered."
        ));
        data.put("SprintReview|RARE", List.of(
                "Masterful reviews blend storytelling and technical demonstration to communicate impact.",

                "Sprint Reviews are fertile ground for adaptive planning and realignment of priorities."
        ));


        // Penultimate scrolls:
        data.put("Penultimate|COMMON", List.of(
                "Facing the final boss means confronting the ultimate test of Scrum mastery, Stick to the framework: inspect, adapt, and collaborate."
        ));
        data.put("Penultimate|UNCOMMON", List.of(
                "Strong Definition of Done and cross-functional teamwork can prevent your plans from collapsing under pressure."
        ));
        data.put("Penultimate|RARE", List.of(
                "Only those who embody the Agile mindset — empiricism, courage, and focus — can defeat the boss, Anticipating failure modes and building resilience into the team’s workflow is the key to success."
        ));

        // Default list for general Scrum definitions:
        data.put("Default|COMMON", List.of(
                "Scrum is an agile framework focused on iterative progress.",
                "It emphasizes collaboration, accountability, and continuous improvement.",
                "The Product Owner is responsible for maximizing product value through backlog prioritization."
        ));
        data.put("Default|UNCOMMON", List.of(
                "Scrum implementations vary, but common elements include daily stand-ups and sprints.",
                "A good Scrum team understands the importance of self-organization and adapts quickly.",
                "The Scrum Master facilitates team performance and shields the team from distractions."
        ));
        data.put("Default|RARE", List.of(
                "Advanced Scrum practices involve deep dives into agile philosophy and lean methodologies.",
                "Mastery of Scrum means tailoring its principles as projects evolve, unlocking hidden efficiencies.",
                "A cross-functional Scrum Team combines all skills necessary to deliver a usable product increment."
        ));

        data.put("Warning|RARE", List.of(
                String.format("❗ A Warning:%n Within the Boss Chamber lie three enigmatic trials—each a riddle whose failure portends in a game over.")
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

