package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Providers.FunnyHintProvider;
import org.ScrumEscapeGame.Providers.HelpHintProvider;
import org.ScrumEscapeGame.Providers.QuestionWithHints;

import java.util.*;

/**
 * Stores predefined questions for rooms based on their IDs.
 * Allows retrieval of a question for a specific room.
 */
public class RoomQuestions {
    // Stores predefined questions for room IDs.
    private static final Map<Integer, QuestionWithHints> QUESTIONS = new HashMap<>();
    private static final List<Question> QUESTIONBOSS = new ArrayList<>();

    static {
        QUESTIONS.put(1, new QuestionWithHints(
                new Question(
                "What is the purpose of backlog refinement in Scrum?",
                List.of(
                        "To assign tasks to team members",
                        "To ensure the backlog is ready for future sprints",
                        "To report progress to stakeholders",
                        "To define the team structure"
                ),
                "To ensure the backlog is ready for future sprints"
                ),
                List.of(
                        new HelpHintProvider("Focus on preparing future work."),
                        new FunnyHintProvider("Backlog refinement = Scrum’s spring cleaning!")
                )
        ));

        QUESTIONS.put(2, new QuestionWithHints(
                new Question(
                "Who is responsible for managing the Product Backlog?",
                List.of(
                        "Scrum Master",
                        "Product Owner",
                        "Development Team",
                        "Stakeholders"
                ),
                "Product Owner"
                ),
                List.of(
                        new HelpHintProvider("It’s someone who sets priorities."),
                        new FunnyHintProvider("Not the Scrum Master, they’re too busy facilitating!")
                )
        ));

        QUESTIONS.put(3, new QuestionWithHints(
                new Question(
                "How long is a typical Sprint in Scrum?",
                List.of(
                        "1 to 2 days",
                        "1 to 2 weeks",
                        "2 to 4 weeks",
                        "4 to 6 weeks"
                ),
                "2 to 4 weeks"
                ),
                List.of(
                        new HelpHintProvider("Not implemented"),
                        new FunnyHintProvider("Not implemented")
                )
        ));

        QUESTIONS.put(4, new QuestionWithHints(
                new Question(
                "What is the maximum timebox for the Daily Scrum?",
                List.of(
                        "5 minutes",
                        "10 minutes",
                        "15 minutes",
                        "30 minutes"
                ),
                "15 minutes"
                ),
                List.of(
                        new HelpHintProvider("Not implemented"),
                        new FunnyHintProvider("Not implemented")
                )
        ));

        QUESTIONS.put(7, new QuestionWithHints(
                new Question(
                        "Match the Scrum terms to their definitions.",
                        List.of(
                                "Sprint", "Product Backlog", "Scrum Master",
                                "A time-boxed period for development",
                                "An ordered list of everything that is known to be needed in the product",
                                "Responsible for ensuring the team understands and follows Scrum"
                        ),
                        "0,1,2" // Indicates correct matching: term 0 → def at index 0 in the unshuffled list, etc.
                ),
                List.of(
                        new HelpHintProvider("Match each term to what it describes."),
                        new FunnyHintProvider("Scrum Master isn't a cooking term!")
                )
        ));

        // Boss-level questions (no integer key)
        QUESTIONBOSS.add(new Question(
                "What should the Scrum Team do if the Sprint Goal becomes obsolete due to changes in market conditions mid-Sprint?",
                List.of(
                        "The Product Owner cancels the Sprint and refines the Product Backlog",
                        "The Scrum Master extends the Sprint to accommodate new requirements",
                        "The Development Team continues with the remaining work and ignores the change",
                        "The Scrum Master calls an emergency Sprint Review to redefine the Sprint Goal"
                ),
                "The Product Owner cancels the Sprint and refines the Product Backlog"
        ));

        QUESTIONBOSS.add(new Question(
                "How should emergent technical debt be handled if it does not jeopardize the current Sprint Goal?",
                List.of(
                        "The Development Team should immediately fix it within the current Sprint",
                        "The Scrum Master should escalate it to stakeholders for resolution",
                        "The Product Owner should add it to the Product Backlog for future prioritization",
                        "It should be documented in the Definition of Done"
                ),
                "The Product Owner should add it to the Product Backlog for future prioritization"
        ));

        QUESTIONBOSS.add(new Question(
                "During Sprint Planning, what are the two questions the Development Team must answer?",
                List.of(
                        "What can be done this Sprint and how will the chosen work get done?",
                        "Who will be the Product Owner and Scrum Master for the Sprint?",
                        "How many hours each member will work and what tools they will use",
                        "Which stakeholders will be invited to the Sprint Review and Retrospective"
                ),
                "What can be done this Sprint and how will the chosen work get done?"
        ));
    }

    /**
     * Retrieves the question assigned to a specific room ID.
     *
     * @param roomId The ID of the room.
     * @return The question assigned to the room.
     */
    public static QuestionWithHints getQuestionForRoom(int roomId) {
        return QUESTIONS.get(roomId);
    }

    /**
     * Returns all predefined questions.
     *
     * @return An unmodifiable map of all room questions.
     */
    public static Map<Integer, QuestionWithHints> getAll() {
        return Collections.unmodifiableMap(QUESTIONS);
    }

    /**
     * Returns all boss-level questions.
     *
     * @return An unmodifiable list of boss questions.
     */
    public static List<Question> getBossQuestions() {
        return Collections.unmodifiableList(QUESTIONBOSS);
    }
}
