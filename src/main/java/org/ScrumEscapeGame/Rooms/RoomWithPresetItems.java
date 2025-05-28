package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

public class RoomWithPresetItems extends Room {
    /**
     * Constructs a room with an ID and description.
     *
     * @param id          The unique identifier of the room.
     * @param description A brief text describing the room.
     */
    protected RoomWithPresetItems(int id, String description) {
        super(id, description);
    }
}
