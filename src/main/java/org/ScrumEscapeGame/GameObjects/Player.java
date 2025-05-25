package org.ScrumEscapeGame.GameObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player character in the game.
 * The Player class tracks the player's position, status, inventory, and solved rooms.
 */
public class Player {
    private int position;       // The player's current room ID.
    private String status;      // The player's current status (e.g., "BEGINNING", "IN PROGRESS").
    private Inventory inventory; // Stores items collected by the player.
    private List<Integer> solvedRooms; // Tracks which rooms the player has solved.

    /**
     * Initializes a new Player object with default values.
     * - Position starts at 1 (assuming 1 is a valid starting room).
     * - Status is set to "BEGINNING" to indicate the game start.
     * - Inventory is initialized empty.
     * - The solvedRooms list is initialized to track rooms where challenges were solved.
     */
    public Player() {
        this.position = 1;
        this.status = "BEGINNING";
        this.inventory = new Inventory();
        this.solvedRooms = new ArrayList<>();
    }

    /**
     * Retrieves the player's inventory.
     *
     * @return The player's Inventory object.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Retrieves the player's current position.
     *
     * @return The room ID where the player is currently located.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Updates the player's position when moving to a different room.
     *
     * @param nieuwePosition The new room ID the player enters.
     */
    public void setPosition(int nieuwePosition) {
        this.position = nieuwePosition;
    }

    /**
     * Retrieves the player's current status.
     *
     * @return The player's status (e.g., "BEGINNING", "IN PROGRESS").
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the player's status.
     *
     * @param nieuweStatus The new status to assign.
     */
    public void setStatus(String nieuweStatus) {
        this.status = nieuweStatus;
    }

    /**
     * Updates the player's inventory.
     * If `inventory` is null, initializes a new empty inventory.
     *
     * @param inventory The new inventory to assign.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = (inventory != null) ? inventory : new Inventory();
    }

    /**
     * Retrieves the list of solved rooms.
     *
     * @return A list of room IDs where the player successfully solved a challenge.
     */
    public List<Integer> getSolvedRooms() {
        return solvedRooms;
    }

    /**
     * Updates the list of solved rooms.
     * If the provided list is null, initializes a new empty list.
     *
     * @param solvedRooms A list of solved room IDs.
     */
    public void setSolvedRooms(List<Integer> solvedRooms) {
        this.solvedRooms = (solvedRooms != null) ? solvedRooms : new ArrayList<>();
    }

    /**
     * Marks a room as solved.
     * Ensures the room ID is only added if it hasn’t been marked as solved before.
     *
     * @param roomId The room ID to mark as solved.
     */
    public void addSolvedRoom(int roomId) {
        if (!solvedRooms.contains(roomId)) {
            solvedRooms.add(roomId);
        }
    }
}

