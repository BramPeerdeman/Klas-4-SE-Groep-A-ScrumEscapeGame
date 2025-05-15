package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;

import java.util.Map;

public class AnswerCommand implements Command {
    private final Player player;
    private final Map<Integer, Room> rooms;

    public AnswerCommand(Player player, Map<Integer, Room> rooms) {
        this.player = player;
        this.rooms = rooms;
    }

    @Override
    public void execute() {
        int pos = player.getPosition();
        Room currentRoom = rooms.get(pos);
        if (currentRoom instanceof RoomWithQuestion) {
            ((RoomWithQuestion) currentRoom).triggerQuestion(player);
        } else {
            Game.consoleWindow.printMessage("There is no challenge to answer in this room.");
        }
    }
}


