package org.ScrumEscapeGame.Rooms;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the blueprint for a room.
 * Each RoomDefinition holds an identifier, a room type (used to decide
 * which concrete Room to instantiate), and a description.
 */
public class RoomDefinition {
    private final int id;
    private final String type;
    private final String description;

    /**
     * Creates a new room definition.
     *
     * @param id          the room identifier.
     * @param type        the type of room (e.g. "BacklogRefinement", "Planning").
     * @param description a brief description of the room.
     */
    public RoomDefinition(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    /**
     * Returns the room ID.
     *
     * @return the identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the room type.
     *
     * @return the type as a String.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the room description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Provides a list of sample room definitions for testing or default configuration.
     *
     * @return a list of sample RoomDefinition objects.
     */
    public static List<RoomDefinition> sampleDefinitions() {
        List<RoomDefinition> definitions = new ArrayList<>();
        definitions.add(new RoomDefinition(1, "BacklogRefinement", "A gloomy refinement chamber with creaky doors."));
        definitions.add(new RoomDefinition(2, "Planning", "A cold planning room filled with old files and dim lights."));
        definitions.add(new RoomDefinition(3, "SprintBacklog", "A bright room with plexiglass cabinets and a mysterious photo."));
        definitions.add(new RoomDefinition(4, "SprintReview", "A server-filled room where ventilation roars like beastly lungs."));
        definitions.add(new RoomDefinition(5, "Penultimate", "A shadowy chamber rumored to yield hidden keys."));  // New definition.
        definitions.add(new RoomDefinition(6, "Boss", "The final challenge room, with a door that can only be unlocked with keys.")); // New definition.
        return definitions;
    }

}




