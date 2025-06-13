package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Rooms.RoomQuestions;

public class JokerKey extends Item implements Joker {

    public JokerKey(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        player.addKey();
        publisher.publish(new JokerUsedEvent(getId(), getName(), "Key Joker used: You received an extra key!"));
        return true;
    }

    public boolean canBeUsedIn(Room room) {
        return room.getName().equals("Daily Scrum") || room.getName().equals("Review");
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), getDescription()));
    }
}

