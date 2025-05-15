package org.ScrumEscapeGame;


import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.Game;

import java.util.HashMap;

public class Main
{
    public static void main(String[] args) {
        Game game = new Game();
        game.start();  // this opens the ConsoleWindow
    }
}