package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.AAGame.Game;

import java.util.List;

public class MultipleChoiceStrategy implements QuestionStrategy {
    /**
     * Displays the question and its choices using the provided display service.
     * This method no longer blocks for input.
     */
    @Override
    public boolean ask(Player player, Question question, EventPublisher<GameEvent> publisher, DisplayService displayService) {
        // Write out the question prompt and choices.
        displayService.printMessage("❓ " + question.getPrompt());
        List<String> options = question.getChoices();
        char optionLetter = 'a';
        for (String option : options) {
            displayService.printMessage("  " + optionLetter + ") " + option);
            optionLetter++;
        }
        // We return false by default here since the actual evaluation will be done later via the UI.
        return false;
    }

    /**
     * Evaluates the answer provided by the question panel.
     */
    public boolean evaluateAnswer(String providedAnswer, Question question, DisplayService displayService) {
        if (providedAnswer == null || providedAnswer.trim().isEmpty()) {
            displayService.printMessage("❌ No answer provided.");
            return false;
        }
        if (providedAnswer.trim().equalsIgnoreCase(question.getCorrectAnswer())) {
            displayService.printMessage("✅ Correct!");
            return true;
        } else {
            displayService.printMessage("❌ Incorrect.");
            return false;
        }
    }
}








