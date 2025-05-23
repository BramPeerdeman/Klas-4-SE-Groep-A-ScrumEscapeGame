package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

// A direct passage is always open.
public class DirectConnection implements Connection {
    private Room destination;

    public DirectConnection(Room destination) {
        this.destination = destination;
    }

    public boolean canPass() {
        return true;
    }

    public Room getDestination() {
        return destination;
    }
}

