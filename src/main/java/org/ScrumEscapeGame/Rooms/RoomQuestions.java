package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;

import java.util.*;

/**
 * Stores predefined questions for rooms based on their IDs.
 * Allows retrieval of a question for a specific room.
 */
public class RoomQuestions {
    // Stores predefined questions for room IDs.
    private static final Map<Integer, Question> QUESTIONS = new HashMap<>();

    static {
        QUESTIONS.put(1, new Question(
                "What is the purpose of backlog refinement in Scrum?",
                List.of(
                        "To assign tasks to team members",
                        "To ensure the backlog is ready for future sprints",
                        "To report progress to stakeholders",
                        "To define the team structure"
                ),
                "To ensure the backlog is ready for future sprints"
        ));

        QUESTIONS.put(2, new Question(
                "Who is responsible for managing the Product Backlog?",
                List.of(
                        "Scrum Master",
                        "Product Owner",
                        "Development Team",
                        "Stakeholders"
                ),
                "Product Owner"
        ));

        QUESTIONS.put(3, new Question(
                "How long is a typical Sprint in Scrum?",
                List.of(
                        "1 to 2 days",
                        "1 to 2 weeks",
                        "2 to 4 weeks",
                        "4 to 6 weeks"
                ),
                "2 to 4 weeks"
        ));

        QUESTIONS.put(4, new Question(
                "What is the maximum timebox for the Daily Scrum?",
                List.of(
                        "5 minutes",
                        "10 minutes",
                        "15 minutes",
                        "30 minutes"
                ),
                "15 minutes"
        ));
    }

    /**
     * Retrieves the question assigned to a specific room ID.
     *
     * @param roomId The ID of the room.
     * @return The question assigned to the room.
     */
    public static Question getQuestionForRoom(int roomId) {
        return QUESTIONS.get(roomId);
    }

    /**
     * Returns all predefined questions.
     *
     * @return An unmodifiable map of all room questions.
     */
    public static Map<Integer, Question> getAll() {
        return Collections.unmodifiableMap(QUESTIONS);
    }
}

