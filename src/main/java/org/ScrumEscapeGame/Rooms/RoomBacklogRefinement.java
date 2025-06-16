package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;
import org.ScrumEscapeGame.Providers.HintProviderSelector;

public class RoomBacklogRefinement extends RoomWithQuestion {
    public RoomBacklogRefinement(int id, String description, QuestionWithHints question, QuestionStrategy strategy, HintProviderSelector hintProviderSelector) {
        super(id, description, question, strategy, hintProviderSelector, true, true);
    }
}




