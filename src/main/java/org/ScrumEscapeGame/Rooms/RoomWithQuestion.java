package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Observer.Observer;
import org.ScrumEscapeGame.Observer.Subject;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;
import org.ScrumEscapeGame.cli.Game;

import javax.swing.*; // only needed for demo fallback input
import java.util.List;
import java.util.Scanner;

import org.ScrumEscapeGame.cli.Game;

// RoomWithQuestion.java
import java.util.ArrayList;
import java.util.List;

public class RoomWithQuestion extends Room implements Subject {
    private Question question;
    private QuestionStrategy strategy;
    private boolean questionAsked = false;
    private List<Observer> observers = new ArrayList<>();
    private static final boolean DEBUG = true; // debug flag

    public RoomWithQuestion(int id, String description, Question question, QuestionStrategy strategy) {
        super(id, description);
        this.question = question;
        this.strategy = strategy;
    }

    @Override
    public void onEnter(Player player) {
        super.onEnter(player);
        if (question != null && strategy != null && !questionAsked) {
            Game.consoleWindow.printMessage("A challenge awaits in this room. Enter 'Q' to attempt the question.");
        }
    }

    public void triggerQuestion(Player player) {
        if (DEBUG) {
            System.out.println("DEBUG: triggerQuestion() called in RoomWithQuestion id: " + getId());
        }
        if (question != null && strategy != null && !questionAsked) {
            boolean correct = strategy.ask(player, question);
            questionAsked = true;
            if (DEBUG) {
                System.out.println("DEBUG: Question answered. Was the answer correct? " + correct);
            }
            if (correct) {
                notifyObservers();
            } else {
                Game.resetGame();
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        if (DEBUG) {
            System.out.println("DEBUG: Observer added to RoomWithQuestion id: " + getId());
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if (DEBUG) {
            System.out.println("DEBUG: notifyObservers() called in RoomWithQuestion id: " + getId());
        }
        for (Observer observer : observers) {
            observer.update();
        }
    }
}






