package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.Handlers.GameReset;
import org.ScrumEscapeGame.Handlers.GameStart;
import org.ScrumEscapeGame.cli.ConsoleWindow;
import org.ScrumEscapeGame.cli.GameContext;
import org.ScrumEscapeGame.cli.CommandManager;

public class GameCycleManager {
    private final GameContext context;
    private final ConsoleWindow console;

    public GameCycleManager(GameContext context, ConsoleWindow console) {
        this.context = context;
        this.console = console;
    }

    public void beginGame(CommandManager commandManager) {
        GameStart gameStart = new GameStart(commandManager.getCommands());
        gameStart.initialise();
    }

    public static void resetGame() {
        console.printMessage("Wrong answer! The monster gets you! Resetting game...");
        GameReset reset = new GameReset(context);
        reset.reset();
    }
}

