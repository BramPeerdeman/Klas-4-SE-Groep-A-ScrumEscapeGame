package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.ItemObserver;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.Commands.*;
import org.ScrumEscapeGame.AAGame.*;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.RoomInventoryProvider;
import org.ScrumEscapeGame.Rooms.RoomDefinition;
import org.ScrumEscapeGame.Rooms.RoomFactory;
import org.ScrumEscapeGame.Rooms.ZoneConfig;

import java.util.Map;

/**
 * GameStart is responsible for initializing the game when it is first launched.
 * It registers all necessary commands with the CommandManager, creates the starting game map,
 * and performs any initial setup required (such as publishing introductory events).
 */
public class GameStart {
    private final CommandManager commandManager;
    private final GameContext gameContext;
    private final EventPublisher<GameEvent> eventPublisher;
    private final DisplayService displayService;

    /**
     * Constructs a new GameStart instance.
     *
     * @param commandManager the manager responsible for command registration and handling.
     * @param gameContext    the overall game context.
     * @param eventPublisher the event publisher for broadcasting game events.
     * @param displayService the display service, used to forward messages and events.
     */
    public GameStart(CommandManager commandManager,
                     GameContext gameContext,
                     EventPublisher<GameEvent> eventPublisher,
                     DisplayService displayService) {
        this.commandManager = commandManager;
        this.gameContext = gameContext;
        this.eventPublisher = eventPublisher;
        this.displayService = displayService;
    }

    /**
     * Initializes the game by:
     * <ol>
     *   <li>Registering game commands (movement, map, status, save, load, answer, etc.) with the CommandManager.</li>
     *   <li>Building the game map using a new ZoneConfig and RoomFactory.</li>
     * </ol>
     */
    public void initialise() {
        // Register commands and their handlers.
        commandManager.register("look", new LookCommand(gameContext, eventPublisher));
        commandManager.register("map", new MapCommand(gameContext, eventPublisher));
        commandManager.register("status", new StatusCommand(gameContext, eventPublisher));
        commandManager.register("save", new SaveCommand(gameContext, eventPublisher));
        commandManager.register("load", new LoadCommand(gameContext, eventPublisher));
        commandManager.register("w", new MoveCommand("north", gameContext, eventPublisher));
        commandManager.register("a", new MoveCommand("west", gameContext, eventPublisher));
        commandManager.register("s", new MoveCommand("south", gameContext, eventPublisher));
        commandManager.register("d", new MoveCommand("east", gameContext, eventPublisher));
        commandManager.register("inspect", new InspectCommand(gameContext, eventPublisher));
        //Placeholder for the pickup command, we will need to refactor the command system so that it can take arguments!
        commandManager.register("pickup",    new PickUpCommand(gameContext, eventPublisher));

        // The displayService is passed to the AnswerCommand (to be used by room strategies).
        commandManager.register("answer", new AnswerCommand(gameContext, eventPublisher, displayService));

        commandManager.register("toggleInventory", new ToggleInventoryCommand((GameUIService) displayService));


        // Build the map:
        // Within GameStart:
        ZoneConfig zone = new ZoneConfig("Scrum Zone", RoomDefinition.sampleDefinitions());

        // Create and configure a new RoomInventoryProvider.
        RoomInventoryProvider roomInventoryProvider = new RoomInventoryProvider();

        // Create a RoomFactory that now also receives a RoomInventoryProvider.
        RoomFactory roomFactory = new RoomFactory(zone, (GameUIService) displayService, roomInventoryProvider);

        // Use the MapBuilder to generate the room map.
        MapBuilder mapBuilder = new MapBuilder(gameContext, eventPublisher, roomFactory);
        mapBuilder.build();

    }
}





