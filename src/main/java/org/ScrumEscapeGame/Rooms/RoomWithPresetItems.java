package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;

import java.util.Collections;
import java.util.List;

public class RoomWithPresetItems extends Room {

    //AANPASSEN ALS JE JOKERS IN DE ROOM WIL
    @Override
    public List<Joker> getAvailableJokers() {
        return Collections.emptyList();
    }
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
