package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomMapBuilder {
    private final Map<Integer, Room> roomMap = new HashMap<>();

    public RoomMapBuilder addRoom(Room room) {
        roomMap.put(room.getId(), room);
        return this;
    }

    public RoomMapBuilder addRooms(Collection<? extends Room> rooms) {
        for (Room room : rooms) {
            addRoom(room);
        }
        return this;
    }

    // Helper to determine the reverse direction.
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
     * Connect two rooms with an always-open door (a DirectConnection).
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
     * Connect two rooms with a locked door (a LockedDoorConnection).
     * Additionally, if the source room is a RoomWithQuestion, register the connection as an observer.
     */
    // Example snippet from RoomMapBuilder for connecting locked doors
    // In RoomMapBuilder.java
    public RoomMapBuilder connectLocked(int roomId1, String direction, int roomId2) {
        Room room1 = roomMap.get(roomId1);
        Room room2 = roomMap.get(roomId2);
        if (room1 == null || room2 == null) {
            throw new IllegalArgumentException("Invalid room IDs provided for connection.");
        }

        // Create one shared locked door instance.
        LockedDoor sharedDoor = new LockedDoor();

        // Create a connection from room1 to room2.
        LockedDoorConnection connection1to2 = new LockedDoorConnection(room2, sharedDoor);
        // Create the reverse connection from room2 to room1.
        LockedDoorConnection connection2to1 = new LockedDoorConnection(room1, sharedDoor);

        // Set the neighbor connections.
        room1.setNeighbour(direction, connection1to2);
        String reverseDir = getReverseDirection(direction);
        if (reverseDir != null) {
            room2.setNeighbour(reverseDir, connection2to1);
        }

        // If room1 is a RoomWithQuestion, register the door unlock observer.
        if (room1 instanceof RoomWithQuestion) {
            ((RoomWithQuestion) room1).addObserver(new DoorUnlocker(sharedDoor));
        }

        return this;
    }




    public Map<Integer, Room> build() {
        return roomMap;
    }
}


