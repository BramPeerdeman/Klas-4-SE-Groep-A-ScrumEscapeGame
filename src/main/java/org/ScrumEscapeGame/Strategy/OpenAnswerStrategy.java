package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.cli.Game;

public class OpenAnswerStrategy implements QuestionStrategy {
    @Override
    public boolean ask(Player player, Question question) {
        Game.consoleWindow.printMessage("❓ " + question.getPrompt());

        String input = Game.consoleWindow.readLine("Your answer: ").trim();

        if (input.equalsIgnoreCase(question.getCorrectAnswer())) {
            Game.consoleWindow.printMessage("✅ Correct!");
            return true;
        } else {
            Game.consoleWindow.printMessage("❌ Incorrect. Correct answer was: " + question.getCorrectAnswer());
            return false;
        }
    }
}

