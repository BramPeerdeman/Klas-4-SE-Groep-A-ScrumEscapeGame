package org.ScrumEscapeGame.AAEvents;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.Items.TestItem;

public class ChooseJokerEvent implements GameEvent {
    private Inventory inventory;

    public ChooseJokerEvent (Inventory inventory){
        this.inventory = inventory;
    }

    @Override
    public void apply(GameUIService uiService) {
        String jokerChoice = uiService.readLine("Choose your joker".trim().toLowerCase());
        switch (jokerChoice) {
            case "Hint":
                inventory.addItem(new TestItem(303, "Hint Joker", "1 Free hint for your room."));
                break;
            case "Key":
                inventory.addItem(new TestItem(302, "Key Joker", "1 Free skip for your room."));
                break;
            default:
                uiService.printMessage("Not one of the implemented jokers.");
        }


    }
}
