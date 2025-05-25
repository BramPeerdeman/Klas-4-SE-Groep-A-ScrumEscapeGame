package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.Rooms.LockedDoor;

/**
 * Represents an event that unlocks a door.
 */
public class DoorUnlockedEvent implements GameEvent {
    private final LockedDoor sharedDoor;

    /**
     * Constructs a DoorUnlockedEvent.
     *
     * @param sharedDoor The locked door instance being unlocked.
     */
    public DoorUnlockedEvent(LockedDoor sharedDoor) {
        this.sharedDoor = sharedDoor;
    }

    /**
     * Returns the associated locked door.
     *
     * @return The LockedDoor object.
     */
    public LockedDoor getDoor() {
        return sharedDoor;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage("The door unlocks with a satisfying click!");
        uiService.refreshMapView();
    }
}

