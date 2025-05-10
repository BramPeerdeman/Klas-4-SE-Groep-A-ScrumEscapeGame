package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;

class LookCommand implements Command {
    private Player player;
    public LookCommand(Player player) {
        this.player = player;
    }

    public void execute() {
        Game.consoleWindow.printMessage(Game.rooms.get(player.getPosition()).getDescription());
    }
}
