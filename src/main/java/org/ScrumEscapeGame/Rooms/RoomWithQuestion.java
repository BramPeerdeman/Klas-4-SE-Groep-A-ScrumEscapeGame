package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;
import org.ScrumEscapeGame.cli.Game;

import javax.swing.*; // only needed for demo fallback input
import java.util.List;
import java.util.Scanner;

import org.ScrumEscapeGame.cli.Game;

public class RoomWithQuestion extends Room {
    private Question question;
    private QuestionStrategy strategy;
    private boolean questionAsked = false; // tracks if the challenge has been attempted

    public RoomWithQuestion(int id, String description, Question question, QuestionStrategy strategy) {
        super(id, description);
        this.question = question;
        this.strategy = strategy;
    }

    @Override
    public final void onEnter(Player player) {
        // Standard room entry prints the description.
        super.onEnter(player);
        // Notify the player there is a challenge, but do not trigger it automatically.
        if (question != null && strategy != null && !questionAsked) {
            Game.consoleWindow.printMessage("A challenge awaits in this room. Enter 'Q' to attempt the question.");
        }
    }

    /**
     * Triggers the challenge. Assume the ask() method returns true if answered correctly,
     * false if the answer is wrong.
     */
    public void triggerQuestion(Player player) {
        if (question != null && strategy != null && !questionAsked) {
            boolean correct = strategy.ask(player, question);
            questionAsked = true; // prevent repeated triggering in this room.
            if (!correct) {
                // Wrong answer: reset the game.
                Game.resetGame();
            }
        }
    }
}




