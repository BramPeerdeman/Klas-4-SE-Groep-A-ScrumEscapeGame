package org.ScrumEscapeGame.Items;

public class TestItem extends Item {
    public TestItem(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean isStackable() {
        return false;
    }
}
