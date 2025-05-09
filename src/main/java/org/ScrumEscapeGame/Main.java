package org.ScrumEscapeGame;


import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.Game;

public class Main
{
    public static void main(String[] args) {
        Game game = new Game();
        Room room1 = new Room(1, "The starting room") {
        };
        Room room2 = new Room(2, "The second room") {
        };
        Room room3 = new Room(3, "The third room") {
        };
    }
}