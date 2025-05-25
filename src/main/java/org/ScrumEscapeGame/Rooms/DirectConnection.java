package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

// A direct passage is always open.
/**
 * Represents an open connection between two rooms.
 * Passage is always allowed.
 */
public class DirectConnection implements Connection {
    private Room destination;

    /**
     * Constructs a DirectConnection.
     *
     * @param destination The room this connection leads to.
     */
    public DirectConnection(Room destination) {
        this.destination = destination;
    }

    @Override
    public boolean canPass() {
        return true;
    }

    @Override
    public Room getDestination() {
        return destination;
    }
}


