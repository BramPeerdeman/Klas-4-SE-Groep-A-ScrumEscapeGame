package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomFactory;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;

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
        // called after START button is clicked
        commands.put("look", new LookCommand(player));
        commands.put("map", new MapCommand(player));
        commands.put("status", new StatusCommand(player));
        commands.put("w", new MoveCommand("north", player, rooms));
        commands.put("a", new MoveCommand("west", player, rooms));
        commands.put("s", new MoveCommand("south", player, rooms));
        commands.put("d", new MoveCommand("east", player, rooms));

        List<RoomWithQuestion> roomList = RoomFactory.createRandomizedRooms();
        for (int i = 0; i < roomList.size(); i++)
        {
            rooms.put(roomList.get(i).getId(), roomList.get(i));
        }

        // Connect rooms linearly for simplicity
        for (int i = 0; i < roomList.size() - 1; i++)
        {
            roomList.get(i).setNeighbours("east", roomList.get(i + 1)); // "d"
            roomList.get(i + 1).setNeighbours("west", roomList.get(i)); // "a"
        }

        // Set player start position
        player.setPosition(roomList.get(0).getId());

        // Auto-enter the first room
        roomList.get(0).onEnter(player);

        player.setPosition(1);
        Room startingRoom = rooms.get(player.getPosition());
        startingRoom.onEnter(player); // 👈 only now enter the room!
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



