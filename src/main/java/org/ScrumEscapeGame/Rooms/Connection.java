package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

public interface Connection {
    boolean canPass();
    Room getDestination();
}
