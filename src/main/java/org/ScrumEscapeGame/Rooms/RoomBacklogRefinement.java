package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

public class RoomBacklogRefinement extends RoomWithQuestion
{
    public RoomBacklogRefinement(int id, String description)
    {
        super(id, description, RoomQuestions.getQuestionForRoom(id));
    }
}



