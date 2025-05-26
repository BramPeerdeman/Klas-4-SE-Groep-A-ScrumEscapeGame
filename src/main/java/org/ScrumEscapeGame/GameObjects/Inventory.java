package org.ScrumEscapeGame.GameObjects;

import org.ScrumEscapeGame.Items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items; // Consider using an Item object for richer data

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

