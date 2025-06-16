package org.ScrumEscapeGame.Monster;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Audio.AudioManager;
import org.ScrumEscapeGame.GameObjects.Monster;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class StalkingMonster extends Monster {
    AudioManager audioManager = new AudioManager();
    /**
     * Constructor: vul de naam, beschrijving, publisher en context in.
     * (We zetten 'alive' nog even niet op true—dat gebeurt in spawn().)
     *
     * @param name        de weergavenaam van het monster (bv. “Backlog Beast”)
     * @param description korte tekstuele beschrijving (bv. “Hij bewaakt de Product Backlog…”)
     * @param publisher   de EventPublisher uit GameContext (bijv. context.getPublisher())
     * @param context     de GameContext zelf (in geval je daar informatie uit haalt)
     */
    public StalkingMonster(String name, String description, EventPublisher<?> publisher, GameContext context, int healthPoints) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(name, description, publisher, context, healthPoints);
    }

    @Override
    protected void spawn() {
        if (!isAlive()) {
            audioManager.monsterAppears();
            //TODO spawn logica voor in de ui
        }
    }

    @Override
    public void takeDamage(int damage) {
        healthPoints -= damage;
        audioManager.swordHit();
    }

    @Override
    protected void takeTurn() {

    }

    @Override
    public void die() {
        if (isAlive()) {
            audioManager.monsterDeath();
            //TODO despawn logica voor in de ui
        }
    }
}
