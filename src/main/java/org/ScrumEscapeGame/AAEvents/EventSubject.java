package org.ScrumEscapeGame.AAEvents;

// Make EventSubject generic so it can work with any event type.
public interface EventSubject<T> {
    void addObserver(EventObserver<T> observer);
    void removeObserver(EventObserver<T> observer);
    void notifyObservers(T event);
}

