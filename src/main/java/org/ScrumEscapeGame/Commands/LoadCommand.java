package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Storage.GameStorage;

public class LoadCommand implements Command {
    private final Player player;
    private EventPublisher<GameEvent> publisher;

    public LoadCommand(GameContext player, EventPublisher<GameEvent> publisher) {
        this.player = player;
        this.publisher = publisher;
    }

    @Override
    public void execute() {
        GameStorage.loadGame(player);
    }
}
