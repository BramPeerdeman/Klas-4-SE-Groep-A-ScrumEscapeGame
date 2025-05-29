package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class UseItemEvent implements GameEvent {
    private final int itemId;
    private final String name;
    private final String effectMessage;

    public UseItemEvent(int itemId, String name, String effectMessage) {
        this.itemId = itemId;
        this.name = name;
        this.effectMessage = effectMessage;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getEffectMessage() {
        return effectMessage;
    }

    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage("Item used: " + name + " -> " + effectMessage);
    }
}

