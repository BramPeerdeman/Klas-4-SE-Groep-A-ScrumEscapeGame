package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.Key;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BossLockedDoorConnection extends LockedDoorConnection {
    private final Player player;

    public BossLockedDoorConnection(Room destination, LockedDoor door, Player player) {
        super(destination, door);
        this.player = player;
    }

    @Override
    public boolean canPass() {
        // Do not consume keys automatically.
        // Only allow passage if the door is already unlocked.
        return !door.isLocked();
    }

    public LockedDoor getDoor() {
        return door;
    }
}


