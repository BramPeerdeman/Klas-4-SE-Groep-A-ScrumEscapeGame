package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.AAGame.Game;

public class StatusCommand implements Command
{
    private GameContext context;
    private EventPublisher<GameEvent> publisher;

    public StatusCommand(GameContext context, EventPublisher<GameEvent> publisher)
    {
        this.context = context;
        this.publisher = publisher;
    }

    // When player executes status command, you want to see in which room he/she is and what your status is
    @Override
    public void execute()
    {
        Game.consoleWindow.printMessage("You are currently in room number: " + context.getPlayer().getPosition());
        Game.consoleWindow.printMessage("Your current status is: " + context.getPlayer().getStatus());
    }


}
