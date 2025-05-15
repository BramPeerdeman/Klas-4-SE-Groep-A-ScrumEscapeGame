package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;

public interface QuestionStrategy {
    void ask(Player player, Question question);
}
