package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.AAGame.RoomManager;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.InventoryManager;
import org.ScrumEscapeGame.Rooms.BossLockedDoorConnection;
import org.ScrumEscapeGame.Rooms.Connection;
import org.ScrumEscapeGame.Rooms.LockedDoor;

import java.util.Optional;
import java.util.Set;


/**
 * BossDoorUnlockHandler mediates boss door unlocking.
 * It listens for an UnlockAttemptEvent, checks for a BossLockedDoorConnection
 * among the current room's neighbor connections, and, if one is found,
 * attempts to unlock the boss door.
 */
public class BossDoorUnlockHandler implements EventObserver<GameEvent> {
    private final GameContext context;

    public BossDoorUnlockHandler(GameContext context) {
        this.context = context;
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof UnlockAttemptEvent) {
            // Get the current room based on the player's position.
            int roomId = context.getPlayer().getPosition();
            Room currentRoom = context.getRoomManager().getRoom(roomId);

            // Look for a BossLockedDoorConnection among all neighbor connections.
            Optional<BossLockedDoorConnection> bossConnOpt = currentRoom.getNeighbours().values().stream()
                    .filter(conn -> conn instanceof BossLockedDoorConnection)
                    .map(conn -> (BossLockedDoorConnection) conn)
                    .findFirst();

            if (bossConnOpt.isPresent()) {
                BossLockedDoorConnection bossConn = bossConnOpt.get();
                if (bossConn.canPass()) {
                    // The door is now unlocked.
                    context.getEventPublisher().publish(new DoorUnlockedEvent(bossConn.getDoor()));
                } else {
                    // Not enough keys to unlock the door.
                    context.getEventPublisher().publish(new NotificationEvent("Not enough keys to unlock the boss room."));
                }
            } else {
                context.getEventPublisher().publish(new NotificationEvent("No boss door connection found in this room."));
            }
        }
    }
}



