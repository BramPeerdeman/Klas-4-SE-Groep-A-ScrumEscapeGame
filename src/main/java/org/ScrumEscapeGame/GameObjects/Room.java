package org.ScrumEscapeGame.GameObjects;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.RoomEnteredEvent;
import org.ScrumEscapeGame.Items.BasicInventory;
import org.ScrumEscapeGame.Items.Joker;
import org.ScrumEscapeGame.Rooms.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a room in the game.
 * Rooms contain descriptions and neighbor connections, and they trigger events when entered.
 */
abstract public class Room {
    private int id;                        // Unique room ID.
    private String description;            // Text describing the room.
    private Map<String, Connection> neighbours; // Stores connections to neighboring rooms.
    private int displayOrder;              // Defines the order in which the room is displayed.
    private String name;

    // Inventory is left uninitialized eagerly to save resources:
    private Inventory inventory;

    /**
     * Constructs a room with an ID and description.
     *
     * @param id          The unique identifier of the room.
     * @param description A brief text describing the room.
     */
    protected Room(int id, String description) {
        this.id = id;
        this.description = description;
        this.neighbours = new HashMap<>();
        this.name = name;
    }

    /**
     * Retrieves the room ID.
     *
     * @return The room's unique identifier.
     */
    public int getId() {
        return id;
    }


    public String getName()
    {
        return name;
    }

    /**
     * Retrieves the room description.
     *
     * @return A brief text describing the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Connects this room to another room in the specified direction.
     *
     * @param direction   The direction to the neighboring room ("north", "south", etc.).
     * @param connection  The connection object linking to the destination room.
     */
    public void setNeighbour(String direction, Connection connection) {
        neighbours.put(direction.toLowerCase(), connection);
    }

    /**
     * Retrieves the neighboring room connection in the given direction.
     * If no connection exists in the given direction, returns an empty Optional.
     *
     * @param direction The requested direction ("north", "south", etc.).
     * @return An Optional containing the Connection if found, or empty if not found.
     */
    public Optional<Connection> getNeighbour(String direction) {
        return Optional.ofNullable(neighbours.get(direction.toLowerCase()));
    }

    public Map<String, Connection> getNeighbours() {
        return neighbours;
    }
    public abstract List<Joker> getAvailableJokers();

    /**
     * Handles logic when the player enters this room.
     * - Updates the player's position.
     * - Publishes an event notifying the room has been entered.
     *
     * @param player    The player entering the room.
     * @param publisher The event publisher used to send room entry notifications.
     */
    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        player.setPosition(this.id);
        publisher.publish(new RoomEnteredEvent(description));
    }

    /**
     * Retrieves the room's display order.
     * The display order helps determine the sequence in which rooms appear.
     *
     * @return The display order of the room.
     */
    public int getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Sets the room's display order.
     *
     * @param displayOrder The new display order for the room.
     */
    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Lazy getter for the room’s inventory.
     * If no inventory exists, a new BasicInventory is instantiated.
     */
    public Inventory getInventory() {
        if (inventory == null) {
            inventory = new BasicInventory();
        }
        return inventory;
    }

    // Optionally, you might provide a setter for inventory as well.
    public void setInventory(Inventory inventory) {
        this.inventory = (inventory != null) ? inventory : new BasicInventory();
    }
}




