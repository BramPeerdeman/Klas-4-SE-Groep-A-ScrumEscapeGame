package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;

public class StatusCommand implements Command
{
    private Player player;

    public StatusCommand(Player player)
    {
        this.player = player;
    }

    // When player executes status command, you want to see in which room he/she is and what your status is
    @Override
    public void execute()
    {
        Game.consoleWindow.printMessage("You are currently in room number: " + player.getPosition());
        Game.consoleWindow.printMessage("Your current status is: " + player.getStatus());
    }


}
