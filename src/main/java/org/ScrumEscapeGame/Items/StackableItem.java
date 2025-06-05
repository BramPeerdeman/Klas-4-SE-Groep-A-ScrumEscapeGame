package org.ScrumEscapeGame.Items;

// Stackable item (e.g., potions, coins)
public class StackableItem extends Item {
    private int quantity;

    public StackableItem(int id, String name, String description, int quantity) {
        super(id, name, description);
        this.quantity = quantity;
    }

    @Override
    public boolean isStackable() {
        return true;
    }

    public int getQuantity() { return quantity; }
    public void addQuantity(int amount) { this.quantity += amount; }
    public void removeQuantity(int amount) { this.quantity = Math.max(0, this.quantity - amount); }
}

