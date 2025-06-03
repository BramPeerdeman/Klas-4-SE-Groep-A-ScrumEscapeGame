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

/**
 * Handles multiple-choice questions where the player selects one of several predefined options.
 */
public class MultipleChoiceStrategy implements QuestionStrategy {
    @Override
    public boolean ask(Player player, Question question, EventPublisher<GameEvent> publisher, DisplayService displayService) {
        displayService.printMessage("❓ " + question.getPrompt());

        // Display multiple-choice options.
        List<String> options = question.getChoices();
        char optionLetter = 'a';
        for (String option : options) {
            displayService.printMessage("  " + optionLetter + ") " + option);
            optionLetter++;
        }

        // Read player input and validate selection.
        String input = displayService.readLine("Your answer (a, b, c, d): ").trim().toLowerCase();
        int index = input.charAt(0) - 'a';

        if (index >= 0 && index < options.size() &&
                options.get(index).equalsIgnoreCase(question.getCorrectAnswer())) {
            displayService.printMessage("✅ Correct!");
            return true;
        } else {
            displayService.printMessage("❌ Incorrect.");
            return false;
        }
    }
}






