package org.ScrumEscapeGame.AAEvents;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class ItemInspectEvent implements GameEvent {
    private final int itemId;
    private final String name;
    private final String description;

    public ItemInspectEvent(int itemId, String name, String description) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
    }



    public int getItemId() {
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


