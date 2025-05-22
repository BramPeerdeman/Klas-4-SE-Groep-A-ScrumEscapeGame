package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomFactory;
import org.ScrumEscapeGame.Rooms.RoomMapBuilder;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Rooms.StartingRoom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game
{
    private static Map<String, Command> commands = new HashMap<>();
    public static ConsoleWindow consoleWindow;

    static Player player = new Player();
    /*Huidig gebruikt elke command de spelerspositie en dus elke command heeft een spelerconstructor.
    Kan iemand vinden of dat beter behandelt kan worden?*/

    static HashMap<Integer, Room> rooms = new HashMap<>(); //Hiermee kunnen we in main de map aanmaken.

    public void start() {
        consoleWindow = new ConsoleWindow(this); // 👈 pass Game reference
        consoleWindow.setVisible(true);
    }

    public void beginGame() {

        /*
        BELANGRIJK:
        Als je de Map bouwt via de builder, vergeet niet om de RoomFactory klasse in de Room package aan te passen.
        Waarom? Want de huidige Roomfactory bepaalt de inhoud van de vragen door gebruik te maken van vooraf
        aangegeven Room ID's.
        Als we willen gebruik maken van zone's, dan moeten we een een nieuwe List<RoomWithQuestion> object aanmaken
        EN we moeten dan ook dus een nieuwe createShuffledRooms2() methode aanmaken met nieuwe id's
         */

        // Set up commands (look, map, status, move commands, answer command, etc.)
        commands.put("look", new LookCommand(player));
        commands.put("map", new MapCommand(player));
        commands.put("status", new StatusCommand(player));
        commands.put("save", new SaveCommand(player));
        commands.put("load", new LoadCommand(player));


        commands.put("w", new MoveCommand("north", player, rooms));
        commands.put("a", new MoveCommand("west", player, rooms));
        commands.put("s", new MoveCommand("south", player, rooms));
        commands.put("d", new MoveCommand("east", player, rooms));
        commands.put("answer", new AnswerCommand(player, rooms));

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
        player.setPosition(startRoom.getId());
        startRoom.onEnter(player);

        // IMPORTANT: Now that the rooms are built, refresh the UI:
        Game.consoleWindow.refreshUI();
    }


    /**
     * Resets the game when a question is answered wrong.
     * This clears the current room map, re-shuffles the questions, and sends the
     * player to the start room.
     */
    public static void resetGame() {
        consoleWindow.printMessage("Wrong answer! The monster gets you! Resetting game...");
        // Clear current game rooms.
        rooms.clear();
        // Re-create and shuffle the rooms.

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
        player.setPosition(startRoom.getId());
        startRoom.onEnter(player);

        // IMPORTANT: Now that the rooms are built, refresh the UI:
        Game.consoleWindow.refreshUI();
    }

    public static void handleCommand(String command) {
        Command cmd = commands.get(command);
        if (cmd != null) {
            cmd.execute();
        } else {
            consoleWindow.printMessage("Unknown command: " + command);
        }
    }

    public HashMap<Integer, Room> getRooms() {
        return rooms;
    }
}



