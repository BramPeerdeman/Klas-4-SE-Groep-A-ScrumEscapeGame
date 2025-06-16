package org.ScrumEscapeGame.Monster;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Monster;

public class StatueMonster extends Monster {

    /**
     * Constructor: vul de naam, beschrijving, publisher en context in.
     * (We zetten 'alive' nog even niet op true—dat gebeurt in spawn().)
     *
     * @param name        de weergavenaam van het monster (bv. “Backlog Beast”)
     * @param description korte tekstuele beschrijving (bv. “Hij bewaakt de Product Backlog…”)
     * @param publisher   de EventPublisher uit GameContext (bijv. context.getPublisher())
     * @param context     de GameContext zelf (in geval je daar informatie uit haalt)
     */
    public StatueMonster(String name, String description, EventPublisher<?> publisher, GameContext context) {
        super(name, description, publisher, context);
    }

    @Override
    protected void spawn() {

    }

    @Override
    protected void takeTurn() {

    }

    @Override
    protected void die() {

    }
}
