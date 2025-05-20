package org.ScrumEscapeGame.GameObjects;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int position;
    private String status;
    private Inventory inventory;
    private List<Integer> solvedRooms;

    //methode voor 1e keer opstarten voor speler
    public Player() {
        this.position = 1;
        this.status     = "BEGINNING";
        this.inventory = new Inventory();
        this.solvedRooms = new ArrayList<>();
    }
    public Inventory getInventory() { return inventory; }
    public int getPosition() { return position; }
    public void setPosition(int nieuwePosition) { this.position = nieuwePosition; }

    public String getStatus() { return status; }
    public void setStatus(String nieuweStatus) { this.status = nieuweStatus; }

    public void setInventory(Inventory inventory)
    {
        this.inventory = (inventory != null) ? inventory : new Inventory();
    }

    public List<Integer> getSolvedRooms()
    {
        return solvedRooms;
    }
    public void setSolvedRooms(List<Integer> solvedRooms)
    {
        this.solvedRooms = (solvedRooms != null) ? solvedRooms : new ArrayList<>();
    }

    public void addSolvedRoom(int roomId)
    {
        if (!solvedRooms.contains(roomId))
        {
            solvedRooms.add(roomId);
        }
    }
}
