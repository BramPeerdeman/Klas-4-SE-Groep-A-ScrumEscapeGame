package org.ScrumEscapeGame.AAEvents;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class ItemInspectEvent implements GameEvent {
    private final String itemId;
    private final String description;

    public ItemInspectEvent(String itemId, String description) {
        this.itemId = itemId;
        this.description = description;
    }



    public String getItemId() {
        return itemId;
    }


    public String getDescription() {
        return description;
    }
    @Override
    public void apply(GameUIService uiService) {
        uiService.printMessage(
                "Inspectie van " + itemId + ": " + description);
    }
}


