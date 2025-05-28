package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

public class BossRoom extends Room {
    private LockedDoor bossDoor;

    public BossRoom(int id, String description, LockedDoor bossDoor) {
        super(id, description);
        this.bossDoor = bossDoor;
    }

    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        // Tell the player they’re in a room with a boss door.
        publisher.publish(new NotificationEvent("You have entered the boss room. The door here requires keys to unlock."));
    }
}

