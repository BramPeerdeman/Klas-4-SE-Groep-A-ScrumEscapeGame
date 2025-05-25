package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomDefinition;
import org.ScrumEscapeGame.Rooms.RoomFactory;
import org.ScrumEscapeGame.Rooms.ZoneConfig;

import java.util.Map;

/**
 * Handles resetting the game state, often due to player failure (e.g., an incorrect answer).
 * The reset process typically involves clearing the current room map, reshuffling questions,
 * and sending the player back to the starting room.
 */
public class GameReset {
    private final GameContext context;
    private final EventPublisher<GameEvent> eventPublisher;
    private final DisplayService displayService;

    /**
     * Constructs a GameReset object.
     *
     * @param context         the current game context.
     * @param eventPublisher  the event publisher used to broadcast reset events.
     * @param displayService  the display service (for creating dependent objects like RoomFactory).
     */
    public GameReset(GameContext context, EventPublisher<GameEvent> eventPublisher, DisplayService displayService) {
        this.context = context;
        this.eventPublisher = eventPublisher;
        this.displayService = displayService;
    }

    /**
     * Resets the game. This method clears the current room map, creates a new ZoneConfig (e.g., from sample definitions),
     * instantiates a new RoomFactory, and rebuilds the map via a MapBuilder. Finally, any reset-specific events
     * (like returning the player to the starting room) are published.
     */
    public void reset() {
        // Clear the current room map.
        context.getRoomManager().clearRooms();

        // Create a new zone configuration using sample definitions (or a dedicated zone).
        ZoneConfig zone = new ZoneConfig("Scrum Zone", RoomDefinition.sampleDefinitions());

        // Create a new RoomFactory with the provided DisplayService.
        RoomFactory roomFactory = new RoomFactory(zone, displayService);

        // Build a new map based on the current context and new room definitions.
        MapBuilder mapBuilder = new MapBuilder(context, eventPublisher, roomFactory);
        mapBuilder.build();
    }
}


