package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

/**
 * Represents a connection between two rooms.
 * Implementing classes determine whether passage is possible.
 */
public interface Connection {
    /**
     * Determines whether the player can pass through this connection.
     *
     * @return True if passage is allowed, false if blocked.
     */
    boolean canPass();

    /**
     * Retrieves the destination room that this connection leads to.
     *
     * @return The destination Room object.
     */
    Room getDestination();
}

