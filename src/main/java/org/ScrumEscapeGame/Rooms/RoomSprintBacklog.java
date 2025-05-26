package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Providers.HintProviderSelector;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;

public class RoomSprintBacklog extends RoomWithQuestion {
    public RoomSprintBacklog(int id, String description, Question question, QuestionStrategy strategy, HintProviderSelector hintProviderSelector) {
        super(id, description, question, strategy, hintProviderSelector);
    }
}

