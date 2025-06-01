package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.AAGame.Game;

/**
 * The starting room of the game.
 * When the player enters this room, it provides introductory information,
 * displays game controls, and sets the initial player position.
 */
public class StartingRoom extends Room {
    private Inventory startingInventory;

    /**
     * Constructs a new StartingRoom.
     *
     * @param id          the unique room identifier (often 0).
     * @param explanation the introduction message for the room.
     */
    public StartingRoom(int id, String explanation) {
        super(id, explanation);
    }

    /**
     * Handles actions when the player enters the starting room.
     * It updates the player's position and publishes several notifications,
     * including introductory text and game control hints.
     *
     * @param player    the player entering the room.
     * @param publisher the publisher to send events/messages.
     */
    @Override
    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        // Update the player's position.
        player.setPosition(this.getId());
        // Publish the room description and welcome messages.
        publisher.publish(new NotificationEvent(getDescription()));
        publisher.publish(new NotificationEvent("This is the starting room. Read the introduction and get ready to begin!"));
        publisher.publish(new NotificationEvent("\n--- Controls ---\n" +
                "W - Move north        M - Open the level map\n" +
                "A - Move west         L - Get the lore of the room\n" +
                "S - Move south        K - Check the status of the game\n" +
                "D - Move east         Q - Attempt the room's question\n" +
                "SAVE - Type save to save the game\n"));
        publisher.publish(new NotificationEvent("I - Open/close your inventory (uses mouse controls)"));
        publisher.publish(new NotificationEvent("NOTE: Currently only hardcore mode is functional, the monster instakills!"));
    }

    @Override
    public Inventory getInventory() {
        // Return the preset starting inventory instead of lazily initializing a new one.
        return startingInventory != null ? startingInventory : super.getInventory();
    }

    public void setStartingInventory(Inventory startingInventory) {
        // Set a preconfigured inventory:
        this.startingInventory = startingInventory;
    }
}


