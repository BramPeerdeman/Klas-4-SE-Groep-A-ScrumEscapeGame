package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.Rooms.LockedDoor;

/**
 * Observes door unlock events and unlocks the relevant door.
 */
public class GlobalDoorUnlockObserver extends UIObserverBase implements EventObserver<GameEvent> {


    /**
     * Constructs a GlobalDoorUnlockObserver.
     *
     * @param uiService The UI service to interact with game events.
     */
    public GlobalDoorUnlockObserver(GameUIService uiService) {
        super(uiService); // oproep naar de constructor van UIObserverBase
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof DoorUnlockedEvent doorEvent) {
            LockedDoor door = doorEvent.getDoor();
            if (door.isLocked()) {
                door.unlock();
            }
        }
    }
}



