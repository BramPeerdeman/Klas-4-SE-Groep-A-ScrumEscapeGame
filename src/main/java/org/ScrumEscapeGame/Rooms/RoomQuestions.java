package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RoomQuestions {

    private static final Map<Integer, Question> QUESTIONS = new HashMap<>();

    public static void registerQuestion(int roomId, Question question) {
        QUESTIONS.put(roomId, question);
    }

    public static Question getQuestionForRoom(int roomId) {
        return QUESTIONS.get(roomId);
    }

    public static Map<Integer, Question> getAll() {
        return Collections.unmodifiableMap(QUESTIONS);
    }
}

