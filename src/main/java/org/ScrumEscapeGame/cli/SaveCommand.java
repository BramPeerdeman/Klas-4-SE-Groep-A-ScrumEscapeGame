package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Storage.GameStorage;

public class SaveCommand implements Command {
    private final Player player;

    public SaveCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        GameStorage.saveGame(player);
    }
}
