package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.Rooms.RoomQuestions;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

import java.util.*;

/**
 * RoomFactory is responsible for creating concrete RoomWithQuestion objects
 * from the provided room definitions. It uses the ZoneConfig to obtain the list
 * of definitions and instantiates a room of the appropriate type.
 */
public class RoomFactory {
    // Holds the configuration containing all sample room definitions.
    private final ZoneConfig zoneConfig;
    // A display service that can be used by room strategies if needed.
    private final DisplayService displayService;

    /**
     * Constructs the RoomFactory.
     *
     * @param zoneConfig     holds the list of room definitions.
     * @param displayService allows strategies to output messages if needed.
     */
    public RoomFactory(ZoneConfig zoneConfig, DisplayService displayService) {
        this.zoneConfig = zoneConfig;
        this.displayService = displayService;
    }

    /**
     * Iterates over each room definition and creates a RoomWithQuestion object.
     * The list is then shuffled to randomize the room order.
     *
     * @return a shuffled list of RoomWithQuestion objects.
     */
    public List<RoomWithQuestion> createShuffledRooms() {
        List<RoomWithQuestion> rooms = new ArrayList<>();

        // Create a room for each definition.
        for (RoomDefinition def : zoneConfig.getRoomDefinitions()) {
            rooms.add(createRoom(def));
        }

        // Shuffle rooms to randomize their order.
        Collections.shuffle(rooms);
        return rooms;
    }

    /**
     * Creates a concrete RoomWithQuestion based on the room definition type.
     *
     * @param def the room definition.
     * @return a RoomWithQuestion instance built according to the definition.
     * @throws IllegalArgumentException if the room type is not supported.
     */
    private RoomWithQuestion createRoom(RoomDefinition def) {
        if (def.getType().equals("BacklogRefinement")) {
            return new RoomBacklogRefinement(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy()  // Strategy that doesn't need displayService.
            );
        } else if (def.getType().equals("Planning")) {
            return new RoomPlanning(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy()
            );
        } else if (def.getType().equals("SprintBacklog")) {
            return new RoomSprintBacklog(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy()
            );
        } else if (def.getType().equals("SprintReview")) {
            return new RoomSprintReview(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy()
            );
        } else if (def.getType().equals("ProductBacklog")) {
            return new RoomProductBacklog(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy()
            );
        } else {
            throw new IllegalArgumentException("Unsupported room type: " + def.getType());
        }
    }
}




