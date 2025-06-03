package org.ScrumEscapeGame.GameObjects;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAGame.GameContext;

public abstract class Monster {
    private String name;
    private String description;
    private boolean alive;
    private EventPublisher<GameEvent> publisher;
    private GameContext context;

    public Monster(String name, String description, boolean alive, EventPublisher<GameEvent> publisher, GameContext context) {
        this.name = name;
        this.description = description;
        this.alive = false;
        this.publisher = publisher;
        this.context = context;
    }

    public boolean Dying() {
        return false;
    }
}