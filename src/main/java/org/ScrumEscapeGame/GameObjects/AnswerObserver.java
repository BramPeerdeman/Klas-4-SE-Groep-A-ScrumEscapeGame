package org.ScrumEscapeGame.GameObjects;
import java.util.List;
import java.util.ArrayList;
public interface AnswerObserver {
    void onCorrectAnswer(int roomId);
    void onWrongAnswer(int roomId);


public class AnswerSubject {
    private List<AnswerObserver> observers = new ArrayList<>();

    public void addObserver(AnswerObserver o) {
        observers.add(o);
    }

    public void removeObserver(AnswerObserver o) {
        observers.remove(o);
    }

    public void notifyCorrect(int roomId) {
        for (var o : observers) o.onCorrectAnswer(roomId);
    }

    public void notifyWrong(int roomId) {
        for (var o : observers) o.onWrongAnswer(roomId);
    }
}
