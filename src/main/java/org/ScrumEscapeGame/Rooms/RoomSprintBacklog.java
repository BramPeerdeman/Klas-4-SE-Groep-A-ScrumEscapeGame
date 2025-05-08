package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

public class RoomSprintBacklog extends Room
{
    protected RoomSprintBacklog(int id, String description) {
        super(id, description);
    }

    @Override
    protected void askChallenge(Player player) {

    }

    @Override
    public void handleInput(String input, Player player) {

    }
}
