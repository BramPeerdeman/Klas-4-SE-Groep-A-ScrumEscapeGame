package org.ScrumEscapeGame.GameObjects;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<String> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public boolean hasItem(String item) {
        return items.contains(item);
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
