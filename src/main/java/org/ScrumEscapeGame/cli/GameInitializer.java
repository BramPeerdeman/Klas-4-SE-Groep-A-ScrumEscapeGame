package org.ScrumEscapeGame.cli;
import java.util.List;
import java.util.Map;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomFactory;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;

public class GameInitializer {
    public static void initialize(Player player, CommandRegistry registry, Map<Integer, Room> rooms) {
        // maak en registreer commands
        registry.register("look", new LookCommand(player));
        // … overige commands …

        // kamers aanmaken, shuffelen en mappen
        List<RoomWithQuestion> roomList = RoomFactory.createShuffledRooms();
        for (RoomWithQuestion r : roomList) rooms.put(r.getId(), r);
        // link buren
        for (int i=0; i<roomList.size()-1; i++) {
            roomList.get(i).setNeighbours("east", roomList.get(i+1));
            roomList.get(i+1).setNeighbours("west", roomList.get(i));
        }
        // startpositie
        Room start = roomList.get(0);
        player.setPosition(start.getId());
        start.onEnter(player);
    }
}

