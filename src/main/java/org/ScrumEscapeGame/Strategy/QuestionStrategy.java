package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;

public interface QuestionStrategy {
    boolean ask(Player player, Question question);
}
