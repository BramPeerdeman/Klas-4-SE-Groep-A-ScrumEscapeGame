package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private final Map<Integer, Room> rooms = new HashMap<>();

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int id) {
        return rooms.get(id);
    }

    public void addRoom(int id, Room room) {
        rooms.put(id, room);
    }

    public void clearRooms() {
        rooms.clear();
    }
}

