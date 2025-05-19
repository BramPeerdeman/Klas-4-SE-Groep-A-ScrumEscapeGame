package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Player player;
    private final CommandRegistry registry;
    private final Map<Integer, Room> rooms;
    private final ConsoleWindow console;
    private final GameEngine engine;

    /**
     * Verantwoordelijkheid:
     *   - Samenstellen (wiring) van alle componenten
     *   - Starten van de GUI en game-loop
     */
    public Game() {
        // 1) Domeinobjecten
        this.player   = new Player();
        this.registry = new CommandRegistry();
        this.rooms    = new HashMap<>();

        // 2) Setup GUI (ConsoleWindow)
        this.console = new ConsoleWindow();

        // 3) Initialisatie van commands, kamers, startpositie
        GameInitializer.initialize(player, registry, rooms);

        // 4) Game-loop engine
        this.engine = new GameEngine(registry, console, player);

        // 5) Console weet hoe het engine aan te roepen
        console.setEngine(engine);
    }

    /**
     * Machtiging om de game te starten:
     * GUI zichtbaar maken en into de loop gaan.
     */
    public void start() {
        console.setVisible(true);
        engine.startLoop();
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
