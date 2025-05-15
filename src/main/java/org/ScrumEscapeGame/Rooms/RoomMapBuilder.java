package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomMapBuilder {
    private final Map<Integer, Room> roomMap = new HashMap<>();

    // Add a single room.
    public RoomMapBuilder addRoom(Room room) {
        roomMap.put(room.getId(), room);
        return this;
    }

    // Bulk-add rooms from a collection.
    public RoomMapBuilder addRooms(Collection<? extends Room> rooms) {
        for (Room room : rooms) {
            addRoom(room);
        }
        return this;
    }

    // Connect two rooms in a specified direction.
    // Optionally, also set the reverse connection automatically.
    public RoomMapBuilder connect(int roomId1, String direction, int roomId2) {
        Room room1 = roomMap.get(roomId1);
        Room room2 = roomMap.get(roomId2);
        if (room1 == null || room2 == null) {
            throw new IllegalArgumentException("Invalid room IDs provided for connection.");
        }
        // Set the connection from room1 to room2.
        room1.setNeighbours(direction, room2);
        // Optionally, set the reverse connection.
        String reverseDir = getReverseDirection(direction);
        if (reverseDir != null) {
            room2.setNeighbours(reverseDir, room1);
        }
        return this;
    }

    // Helper to determine the opposite direction.
    private String getReverseDirection(String direction) {
        return switch (direction.toLowerCase()) {
            case "north" -> "south";
            case "south" -> "north";
            case "east" -> "west";
            case "west" -> "east";
            default -> null;
        };
    }

    // Finalize and obtain the complete room map.
    public Map<Integer, Room> build() {
        return roomMap;
    }
}

