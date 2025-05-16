package org.ScrumEscapeGame.Rooms;

// LockedDoor.java
public class LockedDoor {
    private boolean locked = true;
    private static final boolean DEBUG = true;

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        locked = false;
        if(DEBUG) {
            System.out.println("DEBUG: LockedDoor unlocked.");
        }
    }
}

