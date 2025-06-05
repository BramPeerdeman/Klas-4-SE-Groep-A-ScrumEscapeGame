package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Room;

/**
 * Functional interface representing a function that creates a Room instance
 * from a RoomDefinition and a DisplayService.
 */
public interface RoomCreator {
    /**
     * Creates a new Room based on the provided definition.
     *
     * @param def the RoomDefinition containing the basic room properties.
     * @param displayService a display service used by the room if needed.
     * @return a new Room instance.
     */
    Room create(RoomDefinition def, DisplayService displayService);
}

