package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.*;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAEvents.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MapBuilder creates the game map by:
 * <ol>
 *   <li>Creating a starting room.</li>
 *   <li>Creating additional rooms via the RoomFactory (shuffled for randomness).</li>
 *   <li>Assigning display orders to all rooms.</li>
 *   <li>Connecting rooms using direct and locked door connections.</li>
 *   <li>Injecting the final room map into the RoomManager.</li>
 *   <li>Setting the initial player position and triggering a UI refresh event.</li>
 * </ol>
 */
public class MapBuilder {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;
    private final RoomFactory roomFactory;

    /**
     * Constructs the MapBuilder.
     *
     * @param context     the game context containing rooms, player, and state.
     * @param publisher   the publisher for game events.
     * @param roomFactory the factory for creating rooms.
     */
    public MapBuilder(GameContext context, EventPublisher<GameEvent> publisher, RoomFactory roomFactory) {
        this.context = context;
        this.publisher = publisher;
        this.roomFactory = roomFactory;
    }

    /**
     * Builds the game map by creating and connecting rooms.
     */
    public void build() {
        /*
         * STEP 1: CREATE THE STARTING ROOM
         * ---------------------------------
         * This is the initial room where the player begins.
         * It must have an ID of 0 and be directly accessible.
         */
        StartingRoom startRoom = new StartingRoom(0, "Welcome to the Scrum Escape!");
        startRoom.setDisplayOrder(1);

        /*
         * STEP 2: CREATE ADDITIONAL ROOMS
         * ---------------------------------
         * Each RoomWithQuestion is generated using RoomFactory, ensuring
         * that all rooms defined in RoomDefinition are correctly instantiated.
         * The number of definitions in RoomDefinition must match what we expect here.
         */
        List<RoomWithQuestion> roomList = roomFactory.createShuffledRooms();

        // Assign display orders starting from 2
        for (int i = 0; i < roomList.size(); i++) {
            roomList.get(i).setDisplayOrder(i + 2);
        }

        /*
         * STEP 3: INITIALIZE ROOM MAP
         * ---------------------------------
         * This step registers all rooms into the RoomMapBuilder.
         */
        RoomMapBuilder builder = new RoomMapBuilder()
                .addRoom(startRoom)
                .addRooms(roomList);

        /*
         * STEP 4: CONNECT ROOMS TO DEFINE NAVIGATION
         * ---------------------------------
         * This is where we **design** the actual playable map.
         * Rooms are connected using direct or locked connections.
         * Adjust these relationships carefully to create the intended level design.
         *
         * ---> Game Map Layout Example <---
         *
         *       (START) → [Room 1] → [Room 2] → [Room 3]
         *                     ↓           ↓
         *                 [Room 4]    [Room 5]
         *
         * - Direct Connections allow free passage (e.g., from the start to Room 1).
         * - LockedDoorConnections require solving challenges before passage is allowed.
         */

        // Connect the starting room to the first room.
        builder.connectDirect(startRoom.getId(), "east", roomList.get(0).getId());

        // Connect subsequent rooms using LockedDoorConnections.
        builder.connectLocked(roomList.get(0).getId(), "south", roomList.get(1).getId());
        builder.connectLocked(roomList.get(1).getId(), "east", roomList.get(2).getId());
        builder.connectLocked(roomList.get(2).getId(), "south", roomList.get(3).getId());

        /*
         * STEP 5: FINALIZE ROOM REGISTRATION
         * ---------------------------------
         * Before assigning the map to the game, clear previous rooms
         * and register the newly built map in the RoomManager.
         */
        context.getRoomManager().clearRooms();
        context.getRoomManager().getRooms().putAll(builder.build());

        /*
         * STEP 6: PRINT DEBUG INFORMATION (Optional)
         * ---------------------------------
         * Prints the final room layout with connections. This is useful for testing.
         */
        for (Room r : builder.build().values()) {
            System.out.printf("Room id: %d, display order: %d%n", r.getId(), r.getDisplayOrder());
            for (String dir : new String[]{"north", "south", "east", "west"}) {
                Optional<Connection> cOpt = r.getNeighbour(dir);
                if (cOpt.isPresent()) {
                    System.out.printf("    %s -> %d%n", dir, cOpt.get().getDestination().getId());
                }
            }
        }

        /*
         * STEP 7: SET PLAYER STARTING POSITION
         * ---------------------------------
         * The player's position is set to the starting room.
         */
        context.getPlayer().setPosition(startRoom.getId());
        startRoom.onEnter(context.getPlayer(), publisher);

        /*
         * STEP 8: TRIGGER UI REFRESH EVENT
         * ---------------------------------
         * After initializing the game world, an event is fired to update the map view.
         */
        publisher.publish(new RefreshMapEvent());
    }

}



