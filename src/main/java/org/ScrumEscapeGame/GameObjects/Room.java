package org.ScrumEscapeGame.GameObjects;

import org.ScrumEscapeGame.Rooms.Connection;
import org.ScrumEscapeGame.cli.Game;

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

    public void onEnter(Player player) {
        player.setPosition(this.id);
        Game.consoleWindow.printMessage(description);
    }

    // New getters and setters:
    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}



