package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.Unlockable;


public class UseCommand implements Command {
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;
    private final String targetName;

    public UseCommand(GameContext context, EventPublisher<GameEvent> publisher, String targetName) {
        this.context = context;
        this.publisher = publisher;
        this.targetName = targetName;
    }

    @Override
    public void execute() {
        var inventory = context.getPlayer().getInventory();
        Item found = inventory.getItems().stream()
                .filter(i -> i.getName().equalsIgnoreCase(targetName))
                .findFirst().orElse(null);

        if (found instanceof Unlockable unlockableItem) {
            boolean success = unlockableItem.unlock(context.getPlayer(), publisher);
            // The observer/mediator will handle the unlock logic.
            // If we want to consume the key only after a successful unlock,
            // that removal could be done by the mediator (or via a follow-up event).
            if (success) {
                inventory.removeItem(found);
            }
        } else {
            publisher.publish(new NotificationEvent("Je hebt dit item niet of het is niet bruikbaar."));
        }
    }
}


