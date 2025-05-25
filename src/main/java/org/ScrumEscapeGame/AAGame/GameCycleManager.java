package org.ScrumEscapeGame.AAGame;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAUserInterface.ConsoleWindow;
import org.ScrumEscapeGame.Handlers.GameReset;
import org.ScrumEscapeGame.Handlers.GameStart;
import org.ScrumEscapeGame.Commands.CommandManager;

/**
 * Manages the primary game flow, including starting and resetting the game.
 * It is responsible for initializing the game's starting state and coordinating
 * commands and events at the beginning and upon a game reset (for example, if a challenge is failed).
 */
public class GameCycleManager {
    private final GameContext context;
    private final ConsoleWindow console;
    private final CommandManager commandManager;
    private final EventPublisher<GameEvent> eventPublisher;

    /**
     * Constructs a GameCycleManager.
     *
     * @param context         the overall game context.
     * @param console         the main game window.
     * @param commandManager  the command manager for user commands.
     * @param eventPublisher  the event publisher for game events.
     */
    public GameCycleManager(GameContext context, ConsoleWindow console, CommandManager commandManager, EventPublisher<GameEvent> eventPublisher) {
        this.context = context;
        this.console = console;
        this.commandManager = commandManager;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Begins the game by initializing game state, registering commands, and building the initial map.
     * This method creates a GameStart object and invokes its initialise() method.
     */
    public void beginGame() {
        GameStart gameStart = new GameStart(commandManager, context, eventPublisher, console.getUiService());
        gameStart.initialise();
    }

    /**
     * Resets the game, typically after a wrong answer or a failed challenge.
     * It publishes a notification event, then constructs a GameReset object to rebuild the game map.
     */
    public void resetGame() {
        context.getEventPublisher().publish(new NotificationEvent("Wrong answer! The monster gets you! Resetting game..."));
        GameReset reset = new GameReset(context, eventPublisher, console.getUiService());
        reset.reset();
    }
}


