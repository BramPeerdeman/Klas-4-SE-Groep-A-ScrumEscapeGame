package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Item;


import java.util.Optional;

public class InspectCommand implements Command{
    private final GameContext context;
    private final EventPublisher<GameEvent> publisher;

    public InspectCommand(GameContext context, EventPublisher<GameEvent> publisher) {
        this.context = context;
        this.publisher = publisher;
    }
    @Override
    public void execute() {
        // Haal huidige kamer op
        var room = context.getRoomManager()
                .getRoom(context.getPlayer().getPosition());
        // Loop door alle items en inspecteer alleen de Inspectable-items
        for (Item item : room.getItems()) {
            if (item instanceof Inspectable) {
                ((Inspectable) item).inspect(context.getPlayer(), publisher);
            }
        }
    }
}
