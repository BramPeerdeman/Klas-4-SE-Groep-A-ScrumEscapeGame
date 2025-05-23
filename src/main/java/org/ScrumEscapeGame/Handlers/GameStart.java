package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.Commands.*;
import org.ScrumEscapeGame.AAGame.*;

public class GameStart {
    private final CommandManager commandManager;
    private final GameContext gameContext;
    private final EventPublisher<GameEvent> eventPublisher;

    public GameStart(CommandManager commandManager, GameContext gameContext, EventPublisher<GameEvent> eventPublisher) {
        this.commandManager = commandManager;
        this.gameContext = gameContext;
        this.eventPublisher = eventPublisher;
    }

    public void initialise() {
        // Register commands with their dependencies, not using static globals.
        commandManager.register("look", new LookCommand(gameContext, eventPublisher));
        commandManager.register("map", new MapCommand(gameContext, eventPublisher));
        commandManager.register("status", new StatusCommand(gameContext, eventPublisher));
        commandManager.register("save", new SaveCommand(gameContext, eventPublisher));
        commandManager.register("load", new LoadCommand(gameContext, eventPublisher));
        commandManager.register("w", new MoveCommand("north", gameContext, eventPublisher));
        commandManager.register("a", new MoveCommand("west", gameContext, eventPublisher));
        commandManager.register("s", new MoveCommand("south", gameContext, eventPublisher));
        commandManager.register("d", new MoveCommand("east", gameContext, eventPublisher));
        commandManager.register("answer", new AnswerCommand(gameContext, eventPublisher));
        // Build the map as needed.
        new MapBuilder().build();
    }
}

