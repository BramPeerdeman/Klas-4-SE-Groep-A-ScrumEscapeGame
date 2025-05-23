package org.ScrumEscapeGame.AAGame;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.GameObjects.Player;

public class GameContext {
    private final Player player;
    private final RoomManager roomManager;
    private final EventPublisher<GameEvent> publisher;

    public GameContext(Player player, RoomManager roomManager, EventPublisher<GameEvent> publisher) {
        this.player = player;
        this.roomManager = roomManager;
        this.publisher = publisher;
    }

    public Player getPlayer() {
        return player;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public EventPublisher<GameEvent> getEventPublisher() {
        return publisher;
    }
}
