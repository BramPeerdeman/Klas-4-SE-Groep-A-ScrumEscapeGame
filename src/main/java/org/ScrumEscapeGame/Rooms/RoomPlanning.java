package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;

public class RoomPlanning extends RoomWithQuestion {
    public RoomPlanning(int id, String description, Question question, QuestionStrategy strategy) {
        super(id, description, question, strategy);
    }
}

