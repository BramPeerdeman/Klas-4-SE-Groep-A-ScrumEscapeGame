package org.ScrumEscapeGame.cli;

class LookCommand implements Command {
    public void execute() {
        Game.consoleWindow.printMessage("You are in a dark room. There is a door to the north.");
    }
}
