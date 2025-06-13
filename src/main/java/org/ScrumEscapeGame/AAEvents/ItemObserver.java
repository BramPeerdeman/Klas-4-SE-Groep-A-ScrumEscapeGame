package org.ScrumEscapeGame.AAEvents;


import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class ItemObserver extends UIObserverBase implements EventObserver<GameEvent> {


    /**
     * Constructs a ItemObserver.
     *
     * @param uiService The UI service to interact with game events.
     */
    public ItemObserver(GameUIService uiService) {
        super(uiService); // oproep naar de constructor van UIObserverBase
    }
    @Override
    public void update(GameEvent e) {
        if (e instanceof ItemInspectEvent inspect) {
            uiService.printMessage(
                    "Inspectie van " + inspect.getItemId() + ": " +
                            inspect.getDescription()
            );
        }
        // laat andere events met bestaande logic ongewijzigd
    }
}

