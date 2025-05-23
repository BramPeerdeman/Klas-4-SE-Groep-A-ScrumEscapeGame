package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.AAGame.Game;

import java.util.List;

public class MultipleChoiceStrategy implements QuestionStrategy {
    @Override
    public boolean ask(Player player, Question question) {
        Game.consoleWindow.printMessage("❓ " + question.getPrompt());

        List<String> options = question.getChoices();
        char optionLetter = 'a';
        for (String option : options) {
            Game.consoleWindow.printMessage("  " + optionLetter + ") " + option);
            optionLetter++;
        }

        String input = Game.consoleWindow.readLine("Your answer (a, b, c, d): ").trim().toLowerCase();
        int index = input.charAt(0) - 'a';

        if (index >= 0 && index < options.size() && options.get(index).equalsIgnoreCase(question.getCorrectAnswer())) {
            Game.consoleWindow.printMessage("✅ Correct!");
            return true;
        } else {
            Game.consoleWindow.printMessage("❌ Incorrect. Correct answer was: " + question.getCorrectAnswer());
            return false;
        }
    }
}

