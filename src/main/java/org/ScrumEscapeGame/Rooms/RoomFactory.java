package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.Providers.*;
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

    private List<HintProvider> getHintProvidersForRoom(int roomId) {
        List<HintProvider> providers = new ArrayList<>();

        switch (roomId) {
            case 1:
                providers.add(new HelpHintProvider("Refinement zorgt voor duidelijke user stories."));
                providers.add(new FunnyHintProvider("Heb je de PO wakker gemaakt of slaap je zelf?"));
                break;
            case 2:
                providers.add(new HelpHintProvider("Tijdens planning bepaal je samen het werk voor de sprint."));
                providers.add(new FunnyHintProvider("Planning duurt geen eeuwigheid, toch?"));
                break;
            case 3:
                providers.add(new HelpHintProvider("De sprint backlog is een selectie uit de product backlog."));
                providers.add(new FunnyHintProvider("De Scrum Master zou hier niet blij mee zijn."));
                break;
            default:
                providers.add(new HelpHintProvider("Scrum draait om samenwerking en inspectie."));
                providers.add(new FunnyHintProvider("Zelfs een rubber duck zou dit snappen."));
                break;
        }

        return providers;
    }

    /**
     * Creates a concrete RoomWithQuestion based on the room definition type.
     *
     * @param def the room definition.
     * @return a RoomWithQuestion instance built according to the definition.
     * @throws IllegalArgumentException if the room type is not supported.
     */
    private RoomWithQuestion createRoom(RoomDefinition def) {
        List<HintProvider> hintProviders = getHintProvidersForRoom(def.getId());
        HintProviderSelector hintSelector = new RandomHintProviderSelector(hintProviders);
        if (def.getType().equals("BacklogRefinement")) {
            return new RoomBacklogRefinement(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy(),  // Strategy that doesn't need displayService.
                    hintSelector
            );
        } else if (def.getType().equals("Planning")) {
            return new RoomPlanning(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy(),
                    hintSelector
            );
        } else if (def.getType().equals("SprintBacklog")) {
            return new RoomSprintBacklog(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy(),
                    hintSelector
            );
        } else if (def.getType().equals("SprintReview")) {
            return new RoomSprintReview(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy(),
                    hintSelector
            );
        } else if (def.getType().equals("ProductBacklog")) {
            return new RoomProductBacklog(
                    def.getId(),
                    def.getDescription(),
                    RoomQuestions.getQuestionForRoom(def.getId()),
                    new MultipleChoiceStrategy(),
                    hintSelector
            );
        } else {
            throw new IllegalArgumentException("Unsupported room type: " + def.getType());
        }
    }
}




