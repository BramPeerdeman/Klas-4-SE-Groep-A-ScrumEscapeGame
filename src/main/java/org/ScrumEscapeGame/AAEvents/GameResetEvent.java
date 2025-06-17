package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.Audio.AudioManager;

/**
 * Represents a game reset event (triggered by incorrect answers or other failures).
 * Displays a reset message using the UI service.
 */
public class GameResetEvent implements GameEvent {
    private final String message;

    /**
     * Constructs a GameResetEvent.
     *
     * @param message The reset message to display.
     */
    public GameResetEvent(String message) {
        this.message = message;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.removeQuestionPanel();
        uiService.printMessage(message);
        AudioManager.playSound().questionIncorrect();
    }
}