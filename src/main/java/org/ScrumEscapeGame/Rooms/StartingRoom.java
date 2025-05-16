package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.Game;

public class StartingRoom extends Room {
    public StartingRoom(int id, String explanation) {
        super(id, explanation);
    }

    @Override
    public void onEnter(Player player) {
        player.setPosition(this.getId());
        Game.consoleWindow.printMessage(getDescription());
        Game.consoleWindow.printMessage("This is the starting room. Read the introduction and get ready to begin!");
        Game.consoleWindow.printMessage("NOTE: currently only hardcore mode is functional, the monster instakills!");
    }
}

