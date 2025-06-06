package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Rooms.RoomQuestions;

public class JokerKey extends Joker implements Usable, Inspectable {

    public JokerKey(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        int roomId = player.getPosition();
        QuestionWithHints qwh = RoomQuestions.getQuestionForRoom(roomId);
        String message;

        if (qwh != null) {
            // We assume the Question class provides a getter for the correct answer.
            message = "Joker Key used: The correct answer is: " + qwh.getQuestion().getCorrectAnswer();
        } else {
            message = "There is no challenge in this room.";
        }

        publisher.publish(new JokerUsedEvent(getId(), getName(), message));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), getDescription()));
    }
}

