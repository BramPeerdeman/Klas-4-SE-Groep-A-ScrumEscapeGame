package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.cli.Game;

public class MatchingStrategy implements QuestionStrategy {
    @Override
    public boolean ask(Player player, Question question) {
        Game.consoleWindow.printMessage("🧩 Match the Scrum term with its definition!");
        Game.consoleWindow.printMessage(question.getPrompt());
        Game.consoleWindow.printMessage("Functionality not yet implemented.");
        // Return a default; adjust later when the matching functionality is implemented.
        return true;
    }
}

