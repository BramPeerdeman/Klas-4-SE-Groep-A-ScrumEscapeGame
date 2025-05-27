package org.ScrumEscapeGame.AAEvents;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.EventObserver;
import org.ScrumEscapeGame.AAEvents.ItemInspectEvent;



public class ItemObserver implements EventObserver {
    @Override
    public void onEvent(GameEvent e) {
        if (e instanceof ItemInspectEvent inspect) {
            System.out.println(
                    "Inspectie van " + inspect.getItemId() + ": " +
                            inspect.getDescription()
            );
        }
        // laat andere events met bestaande logic ongewijzigd
    }
}

