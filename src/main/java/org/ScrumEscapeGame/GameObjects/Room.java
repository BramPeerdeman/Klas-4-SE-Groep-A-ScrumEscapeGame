package org.ScrumEscapeGame.GameObjects;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.RoomEnteredEvent;
import org.ScrumEscapeGame.Rooms.Connection;
import org.ScrumEscapeGame.AAGame.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract public class Room {
    private int id;
    private String description;
    private Map<String, Connection> neighbours;
    // New field for display purposes:
    private int displayOrder;

    protected Room(int id, String description) {
        this.id = id;
        this.description = description;
        this.neighbours = new HashMap<>();
    }

    // Existing getters and setters ...
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setNeighbour(String direction, Connection connection) {
        neighbours.put(direction.toLowerCase(), connection);
    }

    public Optional<Connection> getNeighbour(String direction) {
        return Optional.ofNullable(neighbours.get(direction.toLowerCase()));
    }


    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        player.setPosition(this.id);
        publisher.publish(new RoomEnteredEvent(description));
    }

    // New getters and setters:
    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}



