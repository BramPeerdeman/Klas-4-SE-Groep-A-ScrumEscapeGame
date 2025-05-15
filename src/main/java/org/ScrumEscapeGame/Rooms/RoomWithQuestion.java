package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;
import org.ScrumEscapeGame.cli.Game;

import javax.swing.*; // only needed for demo fallback input
import java.util.List;
import java.util.Scanner;

public class RoomWithQuestion extends Room {
    private Question question;
    private QuestionStrategy strategy;

    public RoomWithQuestion(int id, String description, Question question, QuestionStrategy strategy) {
        super(id, description);
        this.question = question;
        this.strategy = strategy;
    }

    @Override
    public final void onEnter(Player player) {
        super.onEnter(player);
        if (question != null && strategy != null) {
            strategy.ask(player, question);
        }
    }
}


