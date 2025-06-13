package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Room;

public interface Joker extends Usable, Inspectable {
    boolean canBeUsedIn(Room room);
}
