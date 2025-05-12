package org.ScrumEscapeGame.GameObjects;

import org.ScrumEscapeGame.cli.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract public class Room {
    private int id;
    private String description;
    private Map<String, Room> neighbours;

    protected Room(int id, String description) {
        this.id = id;
        this.description = description;
        this.neighbours = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    /**
     * Optioneel: alleen nodig als je id later wilt wijzigen
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Optioneel: alleen nodig als je omschrijving later wilt wijzigen
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setNeighbours(String richting, Room room) {
        neighbours.put(richting.toLowerCase(), room);
    }

    /**
     * Haal de buurkamer op in de gegeven richting
     */
    public Optional<Room> getNeighbour(String richting) {
        return Optional.ofNullable(neighbours.get(richting.toLowerCase()));
    }

    // update de de locatie van de speler naar huidige kamer
    // het laat ook de beschrijving van de kamer zien en de vraag die je moet beantwoorden
    public void onEnter(Player player) {

        player.setPosition(this.getId());

        Game.consoleWindow.printMessage(description);

    }
}

