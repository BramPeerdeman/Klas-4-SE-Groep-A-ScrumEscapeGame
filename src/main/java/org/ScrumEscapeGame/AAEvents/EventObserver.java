package org.ScrumEscapeGame.AAEvents;

/**
 * Represents an observer in the Observer pattern.
 * Observers receive updates when a relevant game event is published.
 *
 * @param <T> The type of event this observer will handle.
 */
public interface EventObserver<T> {
    /**
     * Called when a relevant event is published.
     *
     * @param event The event to process.
     */
    void update(T event);
}

