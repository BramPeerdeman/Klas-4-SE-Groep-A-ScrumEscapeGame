package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.ItemInspectEvent;
import org.ScrumEscapeGame.GameObjects.Player;

public class Key extends Item implements Unlockable, Inspectable{
    String unlockDoorID;
    int Id;

    //Print voor wat de item is
    public String Description (){
        return "A key, rusted from being in an overgrown science lab. It seems to still be able to unlock a door.";
    }
    @Override
    public void inspect(Player player, EventPublisher<GameEvent> publisher) {
        publisher.publish(new ItemInspectEvent(getId(), Description()));
    }
    @Override
    public boolean unlock(String doorId) {
        // jullie bestaande unlock-logica
    }
}
