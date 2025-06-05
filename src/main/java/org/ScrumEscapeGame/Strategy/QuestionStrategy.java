package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;

/**
 * Defines a strategy for asking questions in rooms.
 * Implementing classes provide specific mechanisms for asking
 * and evaluating questions.
 */
public interface QuestionStrategy {
    /**
     * Asks the question and returns whether the answer was correct.
     *
     * @param player         The current player attempting the question.
     * @param question       The question to be asked.
     * @param publisher      The event publisher to emit game events.
     * @param displayService The display service for UI input and output.
     * @return               True if the player's answer is correct; false otherwise.
     */
    boolean ask(Player player, Question question, EventPublisher<GameEvent> publisher, DisplayService displayService);
}



