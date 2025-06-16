package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.RoomInventoryProvider;
import org.ScrumEscapeGame.Providers.RoomHintProviderSelector;
import org.ScrumEscapeGame.Rooms.RoomQuestions;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

import java.util.*;

/**
 * RoomFactory is responsible for creating concrete Room objects from
 * provided RoomDefinition instances. The factory uses a registration
 * mechanism (a mapping from type strings to RoomCreator functions) so that
 * new room types can be incorporated without altering the core logic.
 *
 * Note: In this refactored approach, the factory returns objects of type Room.
 * This allows us to support various room subclasses (such as RoomWithQuestion,
 * BossRoom, or PenultimateRoom) rather than restricting all rooms to
 * RoomWithQuestion.
 */
public class RoomFactory {
    // Holds the configuration containing all room definitions.
    private final ZoneConfig zoneConfig;
    // A display service that can be used by room strategies if needed.
    private final GameUIService uiService;
    // The inventory provider used to assign inventories to created rooms.
    private final RoomInventoryProvider roomInventoryProvider;
    // A registry mapping room type strings (from RoomDefinition) to creator functions.
    private final Map<String, RoomCreator> roomCreators = new HashMap<>();

    /**
     * Constructs the RoomFactory.
     *
     * @param zoneConfig            holds the list of room definitions.
     * @param displayService        used by room strategies to output messages.
     * @param roomInventoryProvider used to assign Inventories to created rooms.
     */
    public RoomFactory(ZoneConfig zoneConfig, GameUIService displayService, RoomInventoryProvider roomInventoryProvider) {
        this.zoneConfig = zoneConfig;
        this.uiService = displayService;
        this.roomInventoryProvider = roomInventoryProvider;
        // Register default room types.
        registerDefaultRoomCreators();
    }

    /**
     * Registers default room creators for standard room types.
     * New room types (e.g., "Boss", "Penultimate", etc.) can be added here.
     */
    private void registerDefaultRoomCreators() {
        // Standard room: BacklogRefinement.
        boolean helper = true;
        boolean noHelper = false;
        boolean hasStatue = true;
        boolean hasNoStatue = false;
        roomCreators.put("BacklogRefinement", (def, ds) ->
                new RoomWithQuestion(
                        def.getId(),
                        def.getDescription(),
                        RoomQuestions.getQuestionForRoom(def.getId()),
                        new MultipleChoiceStrategy(),  // Using a multiple choice strategy.
                        new RoomHintProviderSelector(RoomQuestions.getQuestionForRoom(def.getId()).getHintProviders()),
                        helper,
                        hasStatue


                )
        );
        // Standard room: Planning.
        roomCreators.put("Planning", (def, ds) ->
                new RoomWithQuestion(
                        def.getId(),
                        def.getDescription(),
                        RoomQuestions.getQuestionForRoom(def.getId()),
                        new MultipleChoiceStrategy(),
                        new RoomHintProviderSelector(RoomQuestions.getQuestionForRoom(def.getId()).getHintProviders()),
                        helper,
                        hasStatue
                )
        );
        // Standard room: SprintBacklog.
        roomCreators.put("SprintBacklog", (def, ds) ->
                new RoomWithQuestion(
                        def.getId(),
                        def.getDescription(),
                        RoomQuestions.getQuestionForRoom(def.getId()),
                        new MultipleChoiceStrategy(),
                        new RoomHintProviderSelector(RoomQuestions.getQuestionForRoom(def.getId()).getHintProviders()),
                        helper,
                        hasStatue
                )
        );
        // Standard room: SprintReview.
        roomCreators.put("SprintReview", (def, ds) ->
                new RoomWithQuestion(
                        def.getId(),
                        def.getDescription(),
                        RoomQuestions.getQuestionForRoom(def.getId()),
                        new MultipleChoiceStrategy(),
                        new RoomHintProviderSelector(RoomQuestions.getQuestionForRoom(def.getId()).getHintProviders()),
                        helper,
                        hasStatue
                )
        );
        // Standard room: ProductBacklog.
        roomCreators.put("ProductBacklog", (def, ds) ->
                new RoomWithQuestion(
                        def.getId(),
                        def.getDescription(),
                        RoomQuestions.getQuestionForRoom(def.getId()),
                        new MultipleChoiceStrategy(),
                        new RoomHintProviderSelector(RoomQuestions.getQuestionForRoom(def.getId()).getHintProviders()),
                        helper,
                        hasStatue
                )
        );
        // New room type: Boss. A new BossRoom type already in your system.
        roomCreators.put("Boss", (def, ds) ->
                new BossRoom(
                        def.getId(),
                        def.getDescription(),
                        new LockedDoor(),
                        new MultipleChoiceStrategy()

                )
        );
        // New room type: Penultimate.
        roomCreators.put("Penultimate", (def, ds) ->
                new PenultimateRoom(def.getId(), def.getDescription())
        );
    }

    /**
     * Creates a shuffled list of Room objects based on the RoomDefinitions
     * contained within the ZoneConfig. For each RoomDefinition, a Room is created using the
     * registered RoomCreator, and an appropriate Inventory is assigned.
     *
     * @return a shuffled list of Room objects.
     */
    public List<Room> createShuffledRooms() {
        List<Room> rooms = new ArrayList<>();

        // Iterate over all room definitions.
        for (RoomDefinition def : zoneConfig.getRoomDefinitions()) {
            // Create a Room using the corresponding creator function.
            Room room = createRoom(def);
            // Assign the correct inventory for this room.
            room.setInventory(roomInventoryProvider.getInventoryFor(room));
            rooms.add(room);
        }

        // Shuffle the rooms to randomize their order.
        Collections.shuffle(rooms);
        return rooms;
    }

    /**
     * Creates a single Room based on the provided RoomDefinition.
     * This method looks up the room type in the registry and calls its creator.
     *
     * @param def the RoomDefinition containing room properties.
     * @return a Room instance built from the definition.
     * @throws IllegalArgumentException if the room type is unsupported.
     */
    private Room createRoom(RoomDefinition def) {
        // Retrieve the creator function for the given room type.
        RoomCreator creator = roomCreators.get(def.getType());
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported room type: " + def.getType());
        }
        // Use the creator function to instantiate a new Room.
        return creator.create(def, uiService);
    }
}




