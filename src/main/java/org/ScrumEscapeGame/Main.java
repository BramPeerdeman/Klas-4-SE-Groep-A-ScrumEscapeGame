package org.ScrumEscapeGame;


import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.Game;

import java.util.HashMap;

public class Main
{
    public static void main(String[] args) {
        Game game = new Game();
        Room room1 = new Room(1, "The starting room") {
        };
        game.getRooms().put(room1.getId(), room1);
        Room room2 = new Room(2, "The second room") {
        };
        game.getRooms().put(room2.getId(), room2);
        Room room3 = new Room(3, "The third room") {
        };
        game.getRooms().put(room3.getId(), room3);

        //east is links, west is rechts.
        room1.setNeighbours("east", room2);
        room2.setNeighbours("west", room1);
        room2.setNeighbours("east", room3);
        room3.setNeighbours("west", room2);

        game.start();
    }
}