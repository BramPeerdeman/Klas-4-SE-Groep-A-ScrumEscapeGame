package org.ScrumEscapeGame.Commands;

import org.ScrumEscapeGame.AAUserInterface.GameUIService;

public class ToggleInventoryCommand implements Command {
    private final GameUIService uiService;

    public ToggleInventoryCommand(GameUIService uiService) {
        this.uiService = uiService;
    }

    @Override
    public void execute(String args) {
        uiService.toggleInventoryPanel();
    }
}

