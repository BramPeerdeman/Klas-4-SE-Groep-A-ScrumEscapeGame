package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.Items.Rarity;

import java.util.List;

public interface Scrolls {
    public String getSubject();
    public List<String> getText();

    List<String> getTexts();

    public Rarity getRarity();
}
