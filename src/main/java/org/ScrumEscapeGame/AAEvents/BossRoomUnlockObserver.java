package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Rooms.BossLockedDoorConnection;

public class BossRoomUnlockObserver implements EventObserver<GameEvent> {
    private final Player player;
    private final BossLockedDoorConnection bossDoorConnection;

    public BossRoomUnlockObserver(Player player, BossLockedDoorConnection bossDoorConnection) {
        this.player = player;
        this.bossDoorConnection = bossDoorConnection;
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof UnlockAttemptEvent) {
            if (bossDoorConnection.canPass()) {
                System.out.println("Boss Room unlocked!");
            } else {
                System.out.println("You still need more keys to unlock the boss room.");
            }
        }
    }
}

