package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.Game;

import javax.swing.*; // only needed for demo fallback input
import java.util.Scanner;

public class RoomWithQuestion extends Room {
    private Question question;

    public RoomWithQuestion(int id, String description, Question question) {
        super(id, description);
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public final void onEnter(Player player) {
        super.onEnter(player);

        if (question != null) {
            Game.consoleWindow.printMessage("Question: " + question.getPrompt());

            // Fallback input if consoleWindow doesn't support readLine
            String input = JOptionPane.showInputDialog("Your answer: ");

            if (question.isCorrect(input)) {
                Game.consoleWindow.printMessage("✅ Correct!");
            } else {
                Game.consoleWindow.printMessage("❌ Incorrect. You can try again or explore elsewhere.");
            }
        }
    }
}
