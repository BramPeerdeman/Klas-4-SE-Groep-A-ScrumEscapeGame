package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;

import java.util.Collections;
import java.util.List;

public class TestRoom extends Room {

    public TestRoom(int id, String description) {
        super(id, description);
    }

    @Override
    public List<Joker> getAvailableJokers() {
        // Voor testdoeleinden: geen jokers beschikbaar
        return Collections.emptyList();
    }
}
