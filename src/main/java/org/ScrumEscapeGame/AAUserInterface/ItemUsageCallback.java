package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.Items.Item;

public interface ItemUsageCallback {
    void useItem(Item item);
    void inspectItem(Item item);
}

