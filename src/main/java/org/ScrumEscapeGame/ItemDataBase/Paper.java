package org.ScrumEscapeGame.ItemDataBase;

import org.ScrumEscapeGame.Items.Rarity;
import java.util.List;

public class Paper implements Scrolls {
    private final String subject;
    private final List<String> texts;
    private final Rarity rarity;

    public Paper(String subject, List<String> texts, Rarity rarity) {
        this.subject = subject;
        this.texts = texts;
        this.rarity = rarity;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public List<String> getTexts() {
        return texts;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }
}