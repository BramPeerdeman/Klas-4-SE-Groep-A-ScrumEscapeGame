package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.cli.RoomManager;

public class GameContext {
    private final Player player;
    private final RoomManager roomManager;

    public GameContext(Player player, RoomManager roomManager) {
        this.player = player;
        this.roomManager = roomManager;
    }

    public Player getPlayer() {
        return player;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }
}
