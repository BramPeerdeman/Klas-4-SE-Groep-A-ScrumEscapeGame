package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.AAGame.Game;

/**
 * Handles matching questions where players match terms with their definitions.
 * Functionality currently not implemented.
 */
public class MatchingStrategy implements QuestionStrategy {
    @Override
    public boolean ask(Player player, Question question, EventPublisher<GameEvent> publisher, DisplayService displayService) {
        displayService.printMessage("🧩 Match the Scrum term with its definition!");
        displayService.printMessage(question.getPrompt());
        displayService.printMessage("Functionality not yet implemented.");
        return true;  // Placeholder return value; to be updated when implemented.
    }
}



