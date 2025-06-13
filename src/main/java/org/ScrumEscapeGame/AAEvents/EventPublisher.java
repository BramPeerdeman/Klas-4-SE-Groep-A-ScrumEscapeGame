package org.ScrumEscapeGame.AAEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * A publisher that notifies subscribed observers when game events occur.
 *
 * @param <T> The type of events this publisher will handle.
 */
public class EventPublisher<T extends GameEvent> {
    private final List<EventObserver<T>> observers = new ArrayList<>();

    /**
     * Registers an observer to receive events.
     *
     * @param observer The observer to add.
     */
    public void addObserver(EventObserver<T> observer) {
        manageObserver(observer, true);
    }

    /**
     * Unregisters an observer from receiving events.
     *
     * @param observer The observer to remove.
     */
    public void removeObserver(EventObserver<T> observer) {
        manageObserver(observer, false);
    }

    private void manageObserver(EventObserver<T> observer, boolean add) {
        if (add) observers.add(observer);
        else observers.remove(observer);
    }

    /**
     * Publishes an event to all registered observers.
     *
     * @param event The event to be sent to observers.
     */
    public void publish(T event) {
        for (EventObserver<T> observer : observers) {
            observer.update(event);
        }
    }
}




