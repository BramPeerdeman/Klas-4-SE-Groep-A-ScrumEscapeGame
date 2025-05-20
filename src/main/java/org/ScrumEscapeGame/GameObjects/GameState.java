package org.ScrumEscapeGame.GameObjects;

import java.util.List;

public class GameState {
    private int playerPosition;
    private List<Integer> solvedRooms;
    private List<String> inventoryItems;

    public GameState(int playerPosition, List<Integer> solvedRooms, List<String> inventoryItems) {
        this.playerPosition = playerPosition;
        this.solvedRooms = solvedRooms;
        this.inventoryItems = inventoryItems;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public List<Integer> getSolvedRooms() {
        return solvedRooms;
    }

    public List<String> getInventoryItems() {
        return inventoryItems;
    }
}
