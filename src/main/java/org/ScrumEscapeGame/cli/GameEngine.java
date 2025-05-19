package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;

public class GameEngine {
    private final CommandRegistry registry;
    private final ConsoleWindow console;
    private final Player player;

    public GameEngine(CommandRegistry registry, ConsoleWindow console, Player player) {
        this.registry  = registry;
        this.console   = console;
        this.player    = player;
    }

    public void startLoop() {
        console.printMessage("Welcome!");
        while (true) {
            String input = console.readInput();
            Command cmd = registry.get(input);
            if (cmd != null) {
                cmd.execute();
            } else {
                console.printMessage("Onbekend commando: " + input);
            }
        }
    }
}

