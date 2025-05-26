package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;
import org.ScrumEscapeGame.Providers.HintProviderSelector;

public class RoomBacklogRefinement extends RoomWithQuestion {
    public RoomBacklogRefinement(int id, String description, Question question, QuestionStrategy strategy, HintProviderSelector hintProviderSelector) {
        super(id, description, question, strategy, hintProviderSelector);
    }
}




