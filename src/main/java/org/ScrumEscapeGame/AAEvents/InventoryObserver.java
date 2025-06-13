package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class InventoryObserver extends UIObserverBase implements EventObserver<GameEvent> {


    public InventoryObserver(GameUIService uiService) {
        super(uiService); // oproep naar de constructor van UIObserverBase
    }

    @Override
    public void update(GameEvent event) {
        if (event instanceof InventoryItemHoveredEvent) {
            //nothing happens yet as of now.
        } else if (event instanceof InventoryItemClickedEvent) {
            //nothing
        } else if (event instanceof InventoryItemActionEvent) {
            //nothing
        } else if (event instanceof InventoryOpenedEvent ||
                event instanceof InventoryClosedEvent ||
                event instanceof InventoryUpdatedEvent) {
            // These events already call their own apply() methods.
            //WARNING: probably cause events to activate twice.
            event.apply(uiService);
        }
    }
}

