package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomFactory;
import org.ScrumEscapeGame.Rooms.RoomMapBuilder;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Rooms.StartingRoom;
import org.ScrumEscapeGame.cli.Game;

import java.util.HashMap;
import java.util.List;

public class MapBuilder {

    public void build() {
        /*
        BELANGRIJK:
        Als je de Map bouwt via de builder, vergeet niet om de RoomFactory klasse in de Room package aan te passen.
        Waarom? Want de huidige Roomfactory bepaalt de inhoud van de vragen door gebruik te maken van vooraf
        aangegeven Room ID's.
        Als we willen gebruik maken van zone's, dan moeten we een een nieuwe List<RoomWithQuestion> object aanmaken
        EN we moeten dan ook dus een nieuwe createShuffledRooms2() methode aanmaken met nieuwe id's
         */

        // Create the starting room (always open from the start).
        // Example insertion in your map-building logic:
        StartingRoom startRoom = new StartingRoom(0, "Welcome ...");
        startRoom.setDisplayOrder(1);
        List<RoomWithQuestion> roomList = RoomFactory.createShuffledRooms();

        // Assume you want the remaining rooms to display in fixed order (2, 3, 4, …):
        for (int i = 0; i < roomList.size(); i++) {
            roomList.get(i).setDisplayOrder(i + 2);
        }


        // Build the map.
        RoomMapBuilder builder = new RoomMapBuilder()
                .addRoom(startRoom)
                .addRooms(roomList);

        // Connect the starting room to the first Question room with an unlocked door:
        builder.connectDirect(startRoom.getId(), "east", roomList.get(0).getId());

        // Connect the remaining rooms with locked doors. For example:
        builder.connectLocked(roomList.get(0).getId(), "south", roomList.get(1).getId());
        builder.connectLocked(roomList.get(1).getId(), "east", roomList.get(2).getId());
        builder.connectLocked(roomList.get(2).getId(), "south", roomList.get(3).getId());

        // Finalize the map.
        Game.rooms.clear();
        Game.rooms.putAll(builder.build());

        // Set initial player position and display the starting room.
        Game.player.setPosition(startRoom.getId());
        startRoom.onEnter(Game.player);

        // IMPORTANT: Now that the rooms are built, refresh the UI:
        Game.consoleWindow.refreshUI();
    }
}
