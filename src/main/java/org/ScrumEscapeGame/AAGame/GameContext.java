package org.ScrumEscapeGame.AAGame;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.Commands.CommandManager;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.Items.InventoryManager;

/**
 * Encapsulates the overall state of the game.
 * Holds references to the player, the room manager (which tracks the current room map),
 * an event publisher for managing game events, and the command manager for input handling.
 */
public class GameContext {
    private final Player player;
    private final RoomManager roomManager;
    private final EventPublisher<GameEvent> publisher;
    private final CommandManager commandManager;
    private final InventoryManager inventoryManager;

    /**
     * Constructs a new GameContext.
     *
     * @param player         the current player.
     * @param roomManager    the room manager, managing the current map of rooms.
     * @param publisher      the event publisher for game events.
     * @param commandManager the command manager for handling player input.
     */
    public GameContext(Player player, RoomManager roomManager, EventPublisher<GameEvent> publisher, CommandManager commandManager, InventoryManager inventoryManager) {
        this.player = player;
        this.roomManager = roomManager;
        this.publisher = publisher;
        this.commandManager = commandManager;
        this.inventoryManager = inventoryManager;
    }

    /**
     * Returns the current player.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the room manager.
     *
     * @return the RoomManager.
     */
    public RoomManager getRoomManager() {
        return roomManager;
    }

    /**
     * Returns the event publisher for game events.
     *
     * @return the EventPublisher.
     */
    public EventPublisher<GameEvent> getEventPublisher() {
        return publisher;
    }

    /**
     * Returns the command manager.
     *
     * @return the CommandManager.
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Returns the inventory manager.
     *
     * @return the InventoryManager.
     */
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}

