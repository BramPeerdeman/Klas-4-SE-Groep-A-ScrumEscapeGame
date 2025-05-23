package org.ScrumEscapeGame.AAEvents;

import java.util.ArrayList;
import java.util.List;

// A simple generic EventPublisher that implements your EventSubject interface.

public class EventPublisher<T> implements EventSubject<T> {
    private final List<EventObserver<T>> observers = new ArrayList<>();

    @Override
    public void addObserver(EventObserver<T> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(EventObserver<T> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(T event) {
        for (EventObserver<T> observer : observers) {
            observer.update(event);
        }
    }

    // Optionally, you could create a publish() method that wraps notifyObservers().
    public void publish(T event) {
        notifyObservers(event);
    }
}


