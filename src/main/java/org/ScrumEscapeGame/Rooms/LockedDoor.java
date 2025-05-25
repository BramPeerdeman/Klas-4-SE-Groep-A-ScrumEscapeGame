package org.ScrumEscapeGame.Rooms;

// LockedDoor.java
/**
 * Represents a locked door in the game.
 * Initially locked, but can be unlocked.
 */
public class LockedDoor {
    private boolean locked = true;
    private static final boolean DEBUG = true;

    /**
     * Determines whether the door is currently locked.
     *
     * @return True if locked; false otherwise.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Unlocks the door, allowing passage.
     */
    public void unlock() {
        locked = false;
        if (DEBUG) {
            System.out.println("DEBUG: LockedDoor unlocked.");
        }
    }
}


