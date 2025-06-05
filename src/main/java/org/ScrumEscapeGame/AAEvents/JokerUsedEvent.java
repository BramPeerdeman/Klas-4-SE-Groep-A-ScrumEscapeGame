package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class JokerUsedEvent implements GameEvent {
    private final int itemId;
    private final String itemName;
    private final String message;

    public JokerUsedEvent(int itemId, String itemName, String message) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.message = message;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage(message);
    }
}

