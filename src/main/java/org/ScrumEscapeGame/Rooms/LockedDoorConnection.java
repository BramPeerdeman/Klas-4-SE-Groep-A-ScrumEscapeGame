package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

/**
 * LockedDoorConnection represents a door connection that can be either locked or unlocked.
 * It uses a shared LockedDoor instance to determine whether passage is allowed.
 */
public class LockedDoorConnection implements Connection {
    private Room destination;
    private LockedDoor door;
    // Set DEBUG to true for logging; false for production.
    private static final boolean DEBUG = true;

    /**
     * Constructs a LockedDoorConnection.
     *
     * @param destination the room this connection leads to.
     * @param door        the shared LockedDoor instance controlling passage.
     */
    public LockedDoorConnection(Room destination, LockedDoor door) {
        this.destination = destination;
        this.door = door;
        if (DEBUG) {
            System.out.println("DEBUG: LockedDoorConnection created with destination id: " + destination.getId());
        }
    }

    /**
     * Indicates whether passage is possible.
     *
     * @return true if the door is unlocked; false otherwise.
     */
    @Override
    public boolean canPass() {
        boolean passable = !door.isLocked();
        if (DEBUG) {
            System.out.println("DEBUG: LockedDoorConnection canPass() called. Door locked? " + door.isLocked());
        }
        return passable;
    }

    /**
     * Returns the destination room.
     *
     * @return the destination Room.
     */
    @Override
    public Room getDestination() {
        return destination;
    }
}






