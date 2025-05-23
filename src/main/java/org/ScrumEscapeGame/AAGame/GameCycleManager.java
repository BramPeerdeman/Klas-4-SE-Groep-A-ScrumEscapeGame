package org.ScrumEscapeGame.AAGame;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAUserInterface.ConsoleWindow;
import org.ScrumEscapeGame.Handlers.GameReset;
import org.ScrumEscapeGame.Handlers.GameStart;
import org.ScrumEscapeGame.Commands.CommandManager;

public class GameCycleManager {
    private final GameContext context;
    private final ConsoleWindow console;
    private final CommandManager commandManager;
    private final EventPublisher<GameEvent> eventPublisher;


    public GameCycleManager(GameContext context, ConsoleWindow console, CommandManager commandManager, EventPublisher<GameEvent> eventPublisher) {
        this.context = context;
        this.console = console;
        this.commandManager = commandManager;
        this.eventPublisher = eventPublisher;
    }

    public void beginGame() {
        GameStart gameStart = new GameStart(commandManager, context, eventPublisher);
        gameStart.initialise();
    }

    public void resetGame() {
        context.getEventPublisher().publish(new NotificationEvent("Wrong answer! The monster gets you! Resetting game..."));
        GameReset reset = new GameReset(context);
        reset.reset();
    }
}

