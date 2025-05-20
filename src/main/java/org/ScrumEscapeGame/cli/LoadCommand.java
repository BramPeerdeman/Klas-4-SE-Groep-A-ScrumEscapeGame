package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Storage.GameStorage;

public class LoadCommand implements Command {
    private final Player player;

    public LoadCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        GameStorage.loadGame(player);
    }
}
