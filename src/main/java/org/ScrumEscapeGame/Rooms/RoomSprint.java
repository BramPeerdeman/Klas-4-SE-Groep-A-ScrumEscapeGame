package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Providers.HintProviderSelector;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;

public class RoomSprint extends RoomWithQuestion {
    public RoomSprint(int id, String description, Question question, QuestionStrategy strategy, HintProviderSelector hintProviderSelector) {
        super(id, description, question, strategy, hintProviderSelector);
    }
}
