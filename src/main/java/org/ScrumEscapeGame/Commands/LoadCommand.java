package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Storage.GameStorage;

public class LoadCommand implements Command {
    private final GameContext context;
    private EventPublisher<GameEvent> publisher;

    public LoadCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    @Override
    public void execute(String args) {
        GameStorage.loadGame(context.getPlayer());
    }
}
