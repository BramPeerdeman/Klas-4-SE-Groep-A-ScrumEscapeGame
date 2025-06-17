package org.ScrumEscapeGame.Monster;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.GameResetEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.GameObjects.Monster;
import org.ScrumEscapeGame.GameObjects.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MonsterManager {
    private static MonsterManager instance;
    private final List<Monster> activeMonsters;
    private final Random random = new Random();

    private MonsterManager() {
        activeMonsters = new ArrayList<>();
    }

    public static synchronized MonsterManager getInstance() {
        if (instance == null) {
            instance = new MonsterManager();
        }
        return instance;
    }

    public void registerActiveMonster(Monster monster) {
        activeMonsters.add(monster);
    }

    public void unregisterMonster(Monster monster) {
        activeMonsters.remove(monster);
    }

    /**
     * Call this method on each player cycle (after each command).
     * Each active monster deals 1 damage to the player.
     * Afterwards, if the player's HP is 0 or less, trigger a game reset.
     */
    public void tick(Player player, EventPublisher<GameEvent> publisher) {
        Iterator<Monster> iterator = activeMonsters.iterator();
        while (iterator.hasNext()) {
            Monster monster = iterator.next();
            if (monster.isAlive()) {
                // Each monster deals 1 damage.
                player.setHitPoints(player.getHitPoints() - 1);
                publisher.publish(new NotificationEvent(monster.getName() + " deals 1 damage!"));
            } else {
                iterator.remove();
            }
        }
        // Check player's HP; if it's 0 or less, trigger a game reset.
        if (player.getHitPoints() <= 0) {
            clearActiveMonsters();
            publisher.publish(new GameResetEvent("Your health has reached 0! Restarting the game."));
        }
    }

    public void clearActiveMonsters() {
        activeMonsters.clear();
    }

}

