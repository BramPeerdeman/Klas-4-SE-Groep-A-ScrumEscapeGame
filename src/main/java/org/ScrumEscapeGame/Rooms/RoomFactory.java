package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.Rooms.RoomQuestions;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.GameObjects.Question;

import java.util.*;

public class RoomFactory {

    public static List<RoomWithQuestion> createRandomizedRooms() {
        List<RoomWithQuestion> rooms = new ArrayList<>();

        for (Map.Entry<Integer, Question> entry : RoomQuestions.getAll().entrySet()) {
            int id = entry.getKey();
            Question question = entry.getValue();
            String description = "You are in room " + id;

            rooms.add(new RoomWithQuestion(id, description, question));
        }

        Collections.shuffle(rooms);
        return rooms;
    }
}
