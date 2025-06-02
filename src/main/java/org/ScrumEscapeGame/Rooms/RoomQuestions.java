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
    private static final Map<Integer, QuestionWithHints> QUESTIONS_WITH_HINTS = new HashMap<>();

    static {
        QUESTIONS_WITH_HINTS.put(1, new QuestionWithHints(
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

        // Repeat for other questions, e.g., room 2
        QUESTIONS_WITH_HINTS.put(2, new QuestionWithHints(
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
    }

    /**
     * Retrieves the question assigned to a specific room ID.
     *
     * @param roomId The ID of the room.
     * @return The question assigned to the room.
     */
    public static QuestionWithHints getQuestionForRoom(int roomId) {
        return QUESTIONS_WITH_HINTS.get(roomId);
    }

    /**
     * Returns all predefined questions.
     *
     * @return An unmodifiable map of all room questions.
     */
    public static Map<Integer, QuestionWithHints> getAll() {
        return Collections.unmodifiableMap(QUESTIONS_WITH_HINTS);
    }
}

