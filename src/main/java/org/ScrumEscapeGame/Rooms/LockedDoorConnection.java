package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Observer.Observer;
import org.ScrumEscapeGame.cli.Game;

// A locked door connection awaits an event (such as solving a challenge).
// LockedDoorConnection.java
public class LockedDoorConnection implements Connection {
    private Room destination;
    private LockedDoor door;
    private static final boolean DEBUG = true;

    public LockedDoorConnection(Room destination, LockedDoor door) {
        this.destination = destination;
        this.door = door;
        if (DEBUG) {
            System.out.println("DEBUG: LockedDoorConnection created with destination id: " + destination.getId());
        }
    }

    @Override
    public boolean canPass() {
        boolean passable = !door.isLocked();
        if (DEBUG) {
            System.out.println("DEBUG: LockedDoorConnection canPass() called. Door locked? " + door.isLocked());
        }
        return passable;
    }

    @Override
    public Room getDestination() {
        return destination;
    }
}





