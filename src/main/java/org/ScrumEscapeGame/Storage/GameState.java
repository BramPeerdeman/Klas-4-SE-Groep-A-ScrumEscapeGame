package org.ScrumEscapeGame.Storage;


import com.google.gson.annotations.SerializedName;
import org.ScrumEscapeGame.Items.Item;

import java.util.List;

public class GameState {
    @SerializedName("playerPosition")
    private int playerPosition;

    @SerializedName("solvedRooms")
    private List<Integer> solvedRooms;

    @SerializedName("inventoryItems")
    private List<Item> inventoryItems;

    public GameState() { }

    public GameState(int playerPosition, List<Integer> solvedRooms, List<Item> inventoryItems) {
        this.playerPosition = playerPosition;
        this.solvedRooms = solvedRooms;
        this.inventoryItems = inventoryItems;
    }

    public int getPlayerPosition() { return playerPosition; }
    public void setPlayerPosition(int playerPosition) { this.playerPosition = playerPosition; }

    public List<Integer> getSolvedRooms() { return solvedRooms; }
    public void setSolvedRooms(List<Integer> solvedRooms) { this.solvedRooms = solvedRooms; }

    public List<Item> getInventoryItems() { return inventoryItems; }
    public void setInventoryItems(List<Item> inventoryItems) { this.inventoryItems = inventoryItems; }
}


