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
        // Setup commands
        commands.put("look", new LookCommand(player));
        commands.put("map", new MapCommand(player));
        commands.put("status", new StatusCommand(player));
        commands.put("w", new MoveCommand("north", player, rooms));
        commands.put("a", new MoveCommand("west", player, rooms));
        commands.put("s", new MoveCommand("south", player, rooms));
        commands.put("d", new MoveCommand("east", player, rooms));

        commands.put("answer", new AnswerCommand(player, rooms));

        StartingRoom startRoom = new StartingRoom(0, "Welcome to the Scrum Escape Game! In this game you must answer questions to progress. Be careful: answer incorrectly and you'll have to start over.");

        List<RoomWithQuestion> roomList = RoomFactory.createShuffledRooms();
        for (RoomWithQuestion room : roomList) {
            rooms.put(room.getId(), room); // ID is still 1–4 but position is shuffled
        }



        // Use the builder to add your starting room and the rest.
        RoomMapBuilder builder = new RoomMapBuilder()
                .addRoom(startRoom)          // Always add the starting room first.
                .addRooms(roomList);

        // Connect the rooms in your layout.
        // For instance, assume the starting room connects to the first shuffled room.
        builder.connect(startRoom.getId(), "east", roomList.get(0).getId());
        // And then arrange the remaining rooms among themselves:
        builder.connect(roomList.get(0).getId(), "south", roomList.get(1).getId());
        builder.connect(roomList.get(1).getId(), "east", roomList.get(2).getId());
        builder.connect(roomList.get(2).getId(), "south", roomList.get(3).getId());

        // Build the complete map.

        Game.rooms.clear();
        Game.rooms.putAll(builder.build());


        // Set up game state: starting room is always at the beginning.
        player.setPosition(startRoom.getId());
        startRoom.onEnter(player);

    }

    /**
     * Resets the game when a question is answered wrong.
     * This clears the current room map, re-shuffles the questions, and sends the
     * player to the start room.
     */
    public static void resetGame() {
        consoleWindow.printMessage("Wrong answer! Resetting game...");
        // Clear current game rooms.
        rooms.clear();
        // Re-create and shuffle the rooms.
        StartingRoom startRoom = new StartingRoom(0, "Welcome to the Scrum Escape Game! In this game you must answer questions to progress. Be careful: answer incorrectly and you'll have to start over.");

        List<RoomWithQuestion> roomList = RoomFactory.createShuffledRooms();
        for (RoomWithQuestion room : roomList) {
            rooms.put(room.getId(), room); // ID is still 1–4 but position is shuffled
        }



        // Use the builder to add your starting room and the rest.
        RoomMapBuilder builder = new RoomMapBuilder()
                .addRoom(startRoom)          // Always add the starting room first.
                .addRooms(roomList);

        // Connect the rooms in your layout.
        // For instance, assume the starting room connects to the first shuffled room.
        builder.connect(startRoom.getId(), "east", roomList.get(0).getId());
        // And then arrange the remaining rooms among themselves:
        builder.connect(roomList.get(0).getId(), "south", roomList.get(1).getId());
        builder.connect(roomList.get(1).getId(), "east", roomList.get(2).getId());
        builder.connect(roomList.get(2).getId(), "south", roomList.get(3).getId());

        // Build the complete map.

        Game.rooms.clear();
        Game.rooms.putAll(builder.build());


        // Set up game state: starting room is always at the beginning.
        player.setPosition(startRoom.getId());
        startRoom.onEnter(player);
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



