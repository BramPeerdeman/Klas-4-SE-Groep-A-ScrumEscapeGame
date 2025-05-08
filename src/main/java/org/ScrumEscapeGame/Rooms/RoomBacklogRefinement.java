package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;

public class RoomBacklogRefinement extends Room
{

    private int id;

    protected RoomBacklogRefinement(int id, String description) {
        super(id, description);
    }

    @Override
    protected void askChallenge(Player player) {

    }

    @Override
    public void handleInput(String input, Player player) {

    }
    //if(player.Position(1)){
        //onEnter(Player player);


}



