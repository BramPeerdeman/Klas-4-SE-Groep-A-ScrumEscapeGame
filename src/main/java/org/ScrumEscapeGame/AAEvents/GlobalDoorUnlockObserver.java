package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.Rooms.LockedDoor;

/**
 * Observes door unlock events and unlocks the relevant door.
 */
public class GlobalDoorUnlockObserver implements EventObserver<GameEvent> {
    private final GameUIService uiService;

    /**
     * Constructs a GlobalDoorUnlockObserver.
     *
     * @param uiService The UI service to interact with game events.
     */
    public GlobalDoorUnlockObserver(GameUIService uiService) {
        this.uiService = uiService;
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



