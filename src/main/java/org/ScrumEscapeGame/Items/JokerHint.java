package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Providers.HintProvider;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Rooms.RoomQuestions;

import java.util.List;
import java.util.Random;

public class JokerHint extends Joker implements Usable, Inspectable {

    public JokerHint(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public boolean use(Player player, EventPublisher<GameEvent> publisher) {
        int roomId = player.getPosition();  // Assume room ID is the player's current position
        QuestionWithHints qwh = RoomQuestions.getQuestionForRoom(roomId);
        String message;

        if (qwh != null) {
            List<HintProvider> hints = qwh.getHintProviders();
            if (hints != null && !hints.isEmpty()) {
                // Pick a random hint from the available hints.
                Random rnd = new Random();
                HintProvider selected = hints.get(rnd.nextInt(hints.size()));
                message = "Joker Hint used: " + selected.getHint();
            } else {
                message = "This room doesn’t offer any hints.";
            }
        } else {
            message = "There is no challenge in this room.";
        }

        // Publish an event to display the message.
        publisher.publish(new JokerUsedEvent(getId(), getName(), message));
        return true;
    }

    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), getName(), getDescription()));
    }
}

