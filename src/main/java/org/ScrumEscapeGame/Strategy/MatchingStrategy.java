package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.cli.Game;

public class MatchingStrategy implements QuestionStrategy {
    @Override
    public void ask(Player player, Question question) {
        // Simpele simulatie
        Game.consoleWindow.printMessage("🧩 Match the Scrum term with its definition!");
        Game.consoleWindow.printMessage(question.getPrompt());
        Game.consoleWindow.printMessage("Functionality not yet implemented."); // Placeholder
    }
}
