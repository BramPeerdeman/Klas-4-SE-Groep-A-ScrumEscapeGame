package org.ScrumEscapeGame.AAEvents;

public interface EventObserver<T> {
    void update(T event);
}

