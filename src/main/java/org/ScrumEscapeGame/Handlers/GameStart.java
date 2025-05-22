package org.ScrumEscapeGame.Handlers;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.cli.*;

import java.util.HashMap;
import java.util.Map;

public class GameStart {
    private Map<String, Command> commands;
    private MapBuilder mapBuilder = new MapBuilder();

    public GameStart(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void initialise() {
        // Set up commands (look, map, status, move commands, answer command, etc.)
        commands.put("look", new LookCommand(Game.player));
        commands.put("map", new MapCommand(Game.player));
        commands.put("status", new StatusCommand(Game.player));
        commands.put("save", new SaveCommand(Game.player));
        commands.put("load", new LoadCommand(Game.player));
        commands.put("w", new MoveCommand("north", Game.player, Game.rooms));
        commands.put("a", new MoveCommand("west", Game.player, Game.rooms));
        commands.put("s", new MoveCommand("south", Game.player, Game.rooms));
        commands.put("d", new MoveCommand("east", Game.player, Game.rooms));
        commands.put("answer", new AnswerCommand(Game.player, Game.rooms));
        mapBuilder.build();
    }
}
