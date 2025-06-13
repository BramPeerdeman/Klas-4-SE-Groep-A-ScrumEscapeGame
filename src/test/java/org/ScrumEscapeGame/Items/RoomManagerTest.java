package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAGame.RoomManager;
import org.ScrumEscapeGame.GameObjects.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomManagerTest {

    private RoomManager roomManager;

    @BeforeEach
    void setUp() {
        roomManager = new RoomManager();
        roomManager.addRoom(1, new TestRoom(1, "Kamer 1"));
        roomManager.addRoom(2, new TestRoom(2, "Kamer 2"));
        roomManager.addRoom(3, new TestRoom(3, "Kamer 3"));
    }

    @Test
    void testValidRoomNumbers() {
        assertNotNull(roomManager.getRoom(1));
        assertNotNull(roomManager.getRoom(3));
    }

    @Test
    void testInvalidLowRoomNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            roomManager.getRoom(0);
        });
        assertEquals("Ongeldig kamernummer: 0", exception.getMessage());
    }

    @Test
    void testInvalidHighRoomNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            roomManager.getRoom(5);
        });
        assertEquals("Ongeldig kamernummer: 5", exception.getMessage());
    }
}
