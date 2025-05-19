package org.ScrumEscapeGame.GameObjects;

import java.util.List;

public class GameState {
    private int playerPosition;
    private List<Integer> solvedRoomIds;

    public GameState(int playerPosition, List<Integer> solvedRoomIds) {
        this.playerPosition = playerPosition;
        this.solvedRoomIds = solvedRoomIds;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public List<Integer> getSolvedRoomIds() {
        return solvedRoomIds;
    }
}
