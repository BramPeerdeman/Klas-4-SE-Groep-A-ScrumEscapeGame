package org.ScrumEscapeGame.GameObjects;

public class Player {
    private int position;
    private String status;
    private Inventory inventory;

    //methode voor 1e keer opstarten voor speler
    public Player() {
        this.position = 1;
        this.status     = "BEGINNING";

    }
    public Inventory getInventory() { return inventory; }
    public int getPosition() { return position; }
    public void setPosition(int nieuwePosition) { this.position = nieuwePosition; }

    public String getStatus() { return status; }
    public void setStatus(String nieuweStatus) { this.status = nieuweStatus; }



}
