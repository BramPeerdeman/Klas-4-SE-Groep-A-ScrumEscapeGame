package org.ScrumEscapeGame.cli;

class MapCommand implements Command {
    public void execute() {
        Game.consoleWindow.printMessage("Displaying map...");
        Game.consoleWindow.displayMap();
    }
}
