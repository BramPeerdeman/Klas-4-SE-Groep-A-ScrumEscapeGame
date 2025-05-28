package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Key;

import java.util.HashSet;
import java.util.Set;

public class BossLockedDoorConnection extends LockedDoorConnection {
    private final Player player;

    public BossLockedDoorConnection(Room destination, LockedDoor door, Player player) {
        super(destination, door); // Inherits basic locked door behavior
        this.player = player;
    }

    @Override
    public boolean canPass() {
        if (!door.isLocked()) {
            return true; // Door is already unlocked
        }
        long keyCount = player.getInventory()
                .getItems().stream()
                .filter(i -> i instanceof Key)
                .count();

        if (keyCount >= 6) {
            door.unlock();
            System.out.println("Boss Room unlocked! Je gebruikte 6 sleutels.");
            return true;
        } else {
            System.out.println("Je hebt nog maar " + keyCount
                    + " sleutels. Je hebt er minstens 6 nodig.");
            return false;
        }
    }

    public LockedDoor getDoor() {
        return door;
    }
}


