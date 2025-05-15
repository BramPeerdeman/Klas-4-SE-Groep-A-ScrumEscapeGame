package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.Game;

import javax.swing.*; // only needed for demo fallback input
import java.util.List;
import java.util.Scanner;

public class RoomWithQuestion extends Room {
    private Question question;
    private boolean questionAsked = false;

    public RoomWithQuestion(int id, String description, Question question) {
        super(id, description);
        this.question = question;
    }

    @Override
    public final void onEnter(Player player) {
        super.onEnter(player);

        if (question != null && !questionAsked) {
            questionAsked = true;
            Game.consoleWindow.printMessage("❓ Question: " + question.getPrompt());

            List<String> options = question.getChoices();
            char optionLetter = 'a';
            for (String option : options) {
                Game.consoleWindow.printMessage("  " + optionLetter + ") " + option);
                optionLetter++;
            }

            String input = Game.consoleWindow.readLine("Your answer (a, b, c, d): ").trim().toLowerCase();

            if (input.matches("[a-d]") && question.isCorrect(input)) {
                Game.consoleWindow.printMessage("✅ Correct!");
            } else {
                Game.consoleWindow.printMessage("❌ Incorrect. Correct answer was: " + question.getCorrectAnswer());
            }
        }
    }
}

