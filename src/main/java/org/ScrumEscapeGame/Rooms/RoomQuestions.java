package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RoomQuestions {

    private static final Map<Integer, Question> QUESTIONS = new HashMap<>();

    static {
        QUESTIONS.put(1, new Question(
                "What is the purpose of backlog refinement in Scrum?",
                "To ensure the backlog is ready for future sprints"
        ));

        QUESTIONS.put(2, new Question(
                "Who is responsible for managing the Product Backlog?",
                "Product Owner"
        ));

        // Add more questions
    }

    public static Question getQuestionForRoom(int roomId) {
        return QUESTIONS.get(roomId);
    }

    public static Map<Integer, Question> getAll() {
        return Collections.unmodifiableMap(QUESTIONS);
    }
}
