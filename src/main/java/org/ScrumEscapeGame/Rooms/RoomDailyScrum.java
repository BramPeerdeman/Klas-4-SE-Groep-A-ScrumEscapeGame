package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

public class RoomDailyScrum extends Room
{

    protected RoomDailyScrum(int id, String description) {
        super(id, description);
    }

    @Override
    protected void askChallenge(Player player) {

    }

    @Override
    public void handleInput(String input, Player player) {

    }
}