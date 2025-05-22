package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.cli.Game;
import org.ScrumEscapeGame.cli.GameContext;

public class GameReset {
    MapBuilder mapBuilder = new MapBuilder();
    private GameContext context;

    public GameReset(GameContext context) {
        this.context = context;
    }

    /**
     * Resets the game when a question is answered wrong.
     * This clears the current room map, re-shuffles the questions, and sends the
     * player to the start room.
     */
    public void reset() {
        // Clear current game rooms.
        context.getRoomManager().clearRooms();
        // Re-create and shuffle the rooms.
        mapBuilder.build();
    }
}
