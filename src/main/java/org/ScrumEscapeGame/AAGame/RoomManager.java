package org.ScrumEscapeGame.AAGame;

import org.ScrumEscapeGame.GameObjects.Room;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the collection of rooms in the game.
 * Provides methods for retrieving rooms by ID, adding new rooms, and clearing the current room map.
 */
public class RoomManager {
    private final HashMap<Integer, Room> rooms = new HashMap<>();

    /**
     * Retrieves the full room mapping.
     *
     * @return A HashMap mapping room IDs to Room objects.
     */
    public HashMap<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * Retrieves a room by its ID.
     *
     * @param id The room ID.
     * @return The Room object corresponding to the ID, or null if not found.
     */
    public Room getRoom(int id) {
        return rooms.get(id);
    }

    /**
     * Adds a room to the manager.
     *
     * @param id   The room ID.
     * @param room The Room object to add.
     */
    public void addRoom(int id, Room room) {
        rooms.put(id, room);
    }

    /**
     * Clears all rooms from the manager.
     */
    public void clearRooms() {
        rooms.clear();
    }
}


