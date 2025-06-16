package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.Items.Inspectable;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.Rarity;
import org.ScrumEscapeGame.Items.Usable;

import java.util.List;

public abstract class AbstractScroll extends Item implements Scrolls, Usable, Inspectable {
    protected final List<String> texts;
    protected final Rarity rarity;

    public AbstractScroll(int id, String name, String description, List<String> texts, Rarity rarity) {
        super(id, name, description);
        this.texts = texts;
        this.rarity = rarity;
    }

    @Override
    public List<String> getText() {
        return texts;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    // Subclasses define their own use and inspect behavior.
}
