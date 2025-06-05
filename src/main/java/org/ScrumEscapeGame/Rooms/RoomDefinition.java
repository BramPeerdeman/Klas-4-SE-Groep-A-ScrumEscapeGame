package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.Items.JokerDefinition;

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
    private final List<JokerDefinition> allowedJokers;

    /**
     * Creates a new room definition.
     *
     * @param id          the room identifier.
     * @param type        the type of room (e.g. "BacklogRefinement", "Planning").
     * @param description a brief description of the room.
     */
    public RoomDefinition(int id, String type, String description,List<JokerDefinition> allowedJokers) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.allowedJokers = allowedJokers;
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
    public List<JokerDefinition> getAllowedJokers() { return allowedJokers; }

    /**
     * Provides a list of sample room definitions for testing or default configuration.
     *
     * @return a list of sample RoomDefinition objects.
     */
    public static List<RoomDefinition> sampleDefinitions() {
        List<RoomDefinition> definitions = new ArrayList<>();
        definitions.add(new RoomDefinition(1, "BacklogRefinement", "A gloomy refinement chamber with creaky doors.", List.of(
                new JokerDefinition(
                        "JokerHint",  // type
                        301,          // itemId
                        "HintJoker",  // name
                        "Geeft een hint bij de refinement-vraag."
                )
        )));
        definitions.add(new RoomDefinition(2, "Planning", "A cold planning room filled with old files and dim lights.", List.of(
                new JokerDefinition(
                        "KeyJoker",
                        201,
                        "KeyJoker",
                        "Geeft een extra sleutel in Review."
                )
        ) ));
        definitions.add(new RoomDefinition(3, "SprintBacklog", "A bright room with plexiglass cabinets and a mysterious photo.", List.of(
                new JokerDefinition(
                        "KeyJoker",   // type
                        104,          // itemId
                        "KeyJoker",   // name
                        "Geeft een extra sleutel in Daily Scrum."
                ),
                new JokerDefinition(
                        "JokerHint",
                        105,
                        "HintJoker",
                        "Geeft een hint bij de Daily Scrum-vraag."
                )
        )));
        definitions.add(new RoomDefinition(4, "SprintReview", "A server-filled room where ventilation roars like beastly lungs.", List.of()));
        definitions.add(new RoomDefinition(5, "Penultimate", "A shadowy chamber rumored to yield hidden keys.", List.of()));  // New definition.
        definitions.add(new RoomDefinition(6, "Boss", "The final challenge room, with a door that can only be unlocked with keys.", List.of())); // New definition.
        return definitions;
    }

}




