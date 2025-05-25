package org.ScrumEscapeGame;


import org.ScrumEscapeGame.AAGame.Game;
import org.ScrumEscapeGame.AAGame.GameComposition;

public class Main {
    public static void main(String[] args) {
        // Instantiate the composition class; this builds all core dependencies.
        GameComposition composition = new GameComposition();

        // Build the game map (rooms, connections, etc.) if not already done in the composition.
        // This ensures that the RoomManager in the GameContext is correctly populated.

        // Create a Game instance using preassembled dependencies from the composition.
        Game game = new Game(
                composition.getGameContext(),
                composition.getConsoleWindow(),
                composition.getCycleManager()
        );

        // Start the game UI.
        game.start();
    }
}
