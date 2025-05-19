package org.ScrumEscapeGame;


import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.*;

import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main(String[] args) {
        Player player = new Player();
        CommandRegistry registry = new CommandRegistry();
        Map<Integer,Room> rooms = new HashMap<>();
        ConsoleWindow console = new ConsoleWindow();

        console.setVisible(true);

        // Initialiseer domein (kamers, spelerpositie, commands)
        GameInitializer.initialize(player, registry, rooms);


        GameInitializer.initialize(player, registry, rooms);
        GameEngine engine = new GameEngine(registry, console, player);
        console.setEngine(engine);
        console.setVisible(true);
        engine.startLoop();
        Game game = new Game();
        game.start();  // this opens the ConsoleWindow
    }
}