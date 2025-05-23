package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.Observer.Observer;
import org.ScrumEscapeGame.AAGame.Game;

// DoorUnlocker.java
public class DoorUnlocker implements Observer {
    private LockedDoor door;

    public DoorUnlocker(LockedDoor door) {
        this.door = door;
    }

    @Override
    public void update() {
        if(door.isLocked()) {
            door.unlock();
            Game.consoleWindow.printMessage("The door unlocks with a satisfying click!");
        }
    }
}

