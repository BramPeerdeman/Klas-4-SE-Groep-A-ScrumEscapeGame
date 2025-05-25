package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * RoomMapBuilder constructs the overall map layout.
 * It collects rooms and allows connecting them with direct or locked doors.
 */
public class RoomMapBuilder {
    // Internal map holding room ID to Room mapping.
    private final Map<Integer, Room> roomMap = new HashMap<>();

    /**
     * Adds a single room to the map.
     *
     * @param room the room to add.
     * @return this builder instance for chaining.
     */
    public RoomMapBuilder addRoom(Room room) {
        roomMap.put(room.getId(), room);
        return this;
    }

    /**
     * Adds a collection of rooms.
     *
     * @param rooms the rooms to add.
     * @return this builder instance for chaining.
     */
    public RoomMapBuilder addRooms(Collection<? extends Room> rooms) {
        for (Room room : rooms) {
            addRoom(room);
        }
        return this;
    }

    /**
     * Returns the reverse of the given direction.
     *
     * @param direction the direction (e.g. "north").
     * @return the opposite direction (e.g. "south"), or null if unknown.
     */
    private String getReverseDirection(String direction) {
        return switch (direction.toLowerCase()) {
            case "north" -> "south";
            case "south" -> "north";
            case "east" -> "west";
            case "west" -> "east";
            default -> null;
        };
    }

    /**
     * Connects two rooms with a direct (always open) connection.
     * Both the forward and reverse connections are set.
     *
     * @param roomId1  the source room ID.
     * @param direction the direction from source to destination.
     * @param roomId2  the destination room ID.
     * @return this builder instance for chaining.
     * @throws IllegalArgumentException if the room IDs are invalid.
     */
    public RoomMapBuilder connectDirect(int roomId1, String direction, int roomId2) {
        Room room1 = roomMap.get(roomId1);
        Room room2 = roomMap.get(roomId2);
        if (room1 == null || room2 == null) {
            throw new IllegalArgumentException("Invalid room IDs provided for connection.");
        }
        room1.setNeighbour(direction, new DirectConnection(room2));
        String reverseDir = getReverseDirection(direction);
        if (reverseDir != null) {
            room2.setNeighbour(reverseDir, new DirectConnection(room1));
        }
        return this;
    }

    /**
     * Connects two rooms with a locked door connection.
     * A shared LockedDoor instance is used between the two directions.
     * In addition, if the room is a RoomWithQuestion, the door is assigned as an associated door.
     *
     * @param roomId1   the source room ID.
     * @param direction the direction from source to destination.
     * @param roomId2   the destination room ID.
     * @return this builder instance for chaining.
     * @throws IllegalArgumentException if the room IDs are invalid.
     */
    public RoomMapBuilder connectLocked(int roomId1, String direction, int roomId2) {
        Room room1 = roomMap.get(roomId1);
        Room room2 = roomMap.get(roomId2);
        if (room1 == null || room2 == null) {
            throw new IllegalArgumentException("Invalid room IDs provided for connection.");
        }
        // Create a shared locked door instance.
        LockedDoor sharedDoor = new LockedDoor();

        // Create connections for both directions.
        LockedDoorConnection connection1to2 = new LockedDoorConnection(room2, sharedDoor);
        LockedDoorConnection connection2to1 = new LockedDoorConnection(room1, sharedDoor);

        // Set neighbors.
        room1.setNeighbour(direction, connection1to2);
        String reverseDir = getReverseDirection(direction);
        if (reverseDir != null) {
            room2.setNeighbour(reverseDir, connection2to1);
        }

        // If rooms implement RoomWithQuestion, register the door.
        if (room1 instanceof RoomWithQuestion) {
            ((RoomWithQuestion) room1).setAssociatedDoor(sharedDoor);
        }
        if (room2 instanceof RoomWithQuestion) {
            ((RoomWithQuestion) room2).setAssociatedDoor(sharedDoor);
        }
        return this;
    }

    /**
     * Returns the assembled room map.
     *
     * @return a Map from room ID to Room.
     */
    public Map<Integer, Room> build() {
        return roomMap;
    }
}



