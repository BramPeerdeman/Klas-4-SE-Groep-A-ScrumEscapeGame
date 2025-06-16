package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.PresetInventory;
import org.ScrumEscapeGame.Items.TestItem;
import org.ScrumEscapeGame.Rooms.*;
import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAEvents.*;

import java.util.*;

/**
 * MapBuilder creates the game map by:
 * <ol>
 *   <li>Creating a starting room.</li>
 *   <li>Creating additional rooms via the RoomFactory (shuffled for randomness).</li>
 *   <li>Separating the standard rooms from the special rooms (Penultimate and Boss).</li>
 *   <li>Ordering the rooms so that any PenultimateRoom comes immediately before any BossRoom.</li>
 *   <li>Assigning display orders to all rooms.</li>
 *   <li>Connecting rooms using direct and locked door connections (and using special connectors for unique links).</li>
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
        // STEP 1: Create the starting room.
        // The StartingRoom (which extends Room) is explicitly created.
        StartingRoom startRoom = new StartingRoom(0, "Welcome to the Scrum Escape!");
        context.getPlayer().setPosition(startRoom.getId());
        startRoom.setDisplayOrder(1);
        // Configure a specialized inventory for the starting room.
        PresetInventory startingInventory = new PresetInventory();
        startingInventory.addItem(new TestItem(301, "Beginner's Key", "A key that starts your journey."));
        startingInventory.addItem(new TestItem(302, "Intro Scroll", "Instructions for your escape."));
        startRoom.setStartingInventory(startingInventory);

        // STEP 2: Create additional rooms via the RoomFactory.
        // Our refactored factory returns a List<Room> containing various room types.
        List<Room> roomList = roomFactory.createShuffledRooms();

        // STEP 3: Separate standard rooms from special rooms.
        List<Room> standardRooms = new ArrayList<>();
        Room penultimateRoom = null;
        Room bossRoom = null;
        for (Room room : roomList) {
            if (room instanceof BossRoom) {
                bossRoom = room;
            } else if (room instanceof PenultimateRoom) {
                penultimateRoom = room;
            } else {
                standardRooms.add(room);
            }
        }
        // Optionally, shuffle the standard rooms again to randomize their order.
        Collections.shuffle(standardRooms);

        // STEP 4: Assemble the final ordered list.
        // The order will be: standardRooms, then penultimateRoom (if present), then bossRoom (if present).
        List<Room> orderedRooms = new ArrayList<>();
        orderedRooms.addAll(standardRooms);
        if (penultimateRoom != null) {
            orderedRooms.add(penultimateRoom);
        }
        if (bossRoom != null) {
            orderedRooms.add(bossRoom);
        }
        // Note: If no special rooms exist, then orderedRooms simply equals standardRooms.

        // STEP 5: Assign display orders.
        // The starting room is order 1, then assign orders sequentially to the rest.
        for (int i = 0; i < orderedRooms.size(); i++) {
            orderedRooms.get(i).setDisplayOrder(i + 2);
        }

        // STEP 6: Initialize the room map using RoomMapBuilder.
        RoomMapBuilder builder = new RoomMapBuilder()
                .addRoom(startRoom)
                .addRooms(orderedRooms);

        // STEP 7: Connect rooms to define navigation.
        // Here we connect adjacent rooms with either direct or locked connections.
        // We assume that a standard locked door connection is used between most rooms.
        // Additionally, if the penultimate and boss rooms are present,
        // you might use a specialized connection between them.
        // Connect the starting room to the first room in orderedRooms.
        if (!orderedRooms.isEmpty()) {
            builder.connectDirect(startRoom.getId(), "east", orderedRooms.get(0).getId());
        }
        // Connect all adjacent rooms in the ordered list using locked door connections.
        for (int i = 0; i < orderedRooms.size() - 1; i++) {
            Room current = orderedRooms.get(i);
            Room next = orderedRooms.get(i + 1);
            // If connecting penultimateRoom to bossRoom, you might want to call a special connection.
            if (current instanceof PenultimateRoom && next instanceof BossRoom) {
                // Use a special method for boss door connections if available.
                // For example, we can use a method called connectBossLocked.
                // Otherwise, default to a standard locked connection.
                builder.connectBossLocked(current.getId(), "east", next.getId(), context.getPlayer());
                System.out.println("DEBUG: Boss connection should've been made!");
            } else {
                builder.connectLocked(current.getId(), "east", next.getId());
            }
        }

        // STEP 8: Finalize room registration.
        context.getRoomManager().clearRooms();
        context.getRoomManager().getRooms().putAll(builder.build());

        // STEP 9: (Optional) Print debug information about the room layout.
        for (Room r : builder.build().values()) {
            System.out.printf("Room id: %d, display order: %d%n", r.getId(), r.getDisplayOrder());
            for (String dir : new String[]{"north", "south", "east", "west"}) {
                Optional<Connection> cOpt = r.getNeighbour(dir);
                if (cOpt.isPresent()) {
                    System.out.printf("    %s -> %d%n", dir, cOpt.get().getDestination().getId());
                }
            }
        }

        // STEP 10: Set the player's starting position.
        context.getPlayer().setPosition(startRoom.getId());
        startRoom.onEnter(context.getPlayer(), publisher);

        // STEP 11: Trigger a UI refresh event to update the map view.
        publisher.publish(new RefreshMapEvent());
    }
}





