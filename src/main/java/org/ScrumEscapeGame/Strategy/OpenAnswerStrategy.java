package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.AAGame.Game;

/**
 * Handles open-ended answer questions where the player must type their response.
 * Validates the input against the correct answer.
 */
public class OpenAnswerStrategy implements QuestionStrategy {
    @Override
    public boolean ask(Player player, Question question, EventPublisher<GameEvent> publisher, DisplayService displayService) {
        displayService.printMessage("❓ " + question.getPrompt());

        // Prompt player for answer.
        String input = displayService.readLine("Your answer: ").trim();

        if (input.equalsIgnoreCase(question.getCorrectAnswer())) {
            displayService.printMessage("✅ Correct!");
            return true;
        } else {
            displayService.printMessage("❌ Incorrect. Correct answer was: " + question.getCorrectAnswer());
            return false;
        }
    }
}



