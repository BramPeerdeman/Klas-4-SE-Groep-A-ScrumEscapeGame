package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.Rooms.RoomQuestions;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.GameObjects.Question;

import java.util.*;

public class RoomFactory {

    public static List<RoomWithQuestion> createShuffledRooms() {
        List<RoomWithQuestion> rooms = new ArrayList<>();

        rooms.add(new RoomBacklogRefinement(1)); // Question 1
        rooms.add(new RoomSprintBacklog(2));     // Question 2
        rooms.add(new RoomDailyScrum(3));        // Question 3
        rooms.add(new RoomSprintReview(4));      // Question 4

        // Shuffle the room list randomly
        Collections.shuffle(rooms);

        return rooms;
    }

}
