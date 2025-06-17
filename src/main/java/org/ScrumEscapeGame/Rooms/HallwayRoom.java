package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;

import java.util.*;

public class HallwayRoom extends Room {
    private Map<String, Connection> neighbours = new HashMap<>();

    public HallwayRoom(int id, int displayOrder) {
        super(id, String.valueOf(displayOrder));
    }

    public void addNeighbour(String label, Connection connection) {
        neighbours.put(label, connection);
    }

    @Override
    public Optional<Connection> getNeighbour(String direction) {
        return Optional.ofNullable(neighbours.get(direction));
    }

    @Override
    public List<Joker> getAvailableJokers() {
        return List.of();
    }

    @Override
    public Map<String, Connection> getAllNeighbours() {
        return neighbours;
    }
}
