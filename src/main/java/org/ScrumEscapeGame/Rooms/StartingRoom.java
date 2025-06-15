package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;

import java.util.Collections;
import java.util.List;

/**
 * The starting room of the game.
 * When the player enters this room, it provides introductory information,
 * displays game controls, and sets the initial player position.
 */
public class StartingRoom extends Room {
    private Inventory startingInventory;

//PAS DIT AAN ALS JE JOKERS IN DE ROOM WIL 
    @Override
    public List<Joker> getAvailableJokers() {
        return Collections.emptyList();
    }

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

        // Publish the room description.
        publisher.publish(new NotificationEvent(getDescription()));

        // Publish a special message stating that a terminal is visible.
        publisher.publish(new NotificationEvent(
                "You enter the starting room and notice a terminal mounted on the wall."
        ));

        publisher.publish(new NotificationEvent(
                "The terminal displays: 'Press R to access the introduction, instructions, and other help.'"
        ));

        // Optionally, include a short message to keep the room's ambience.
        publisher.publish(new NotificationEvent(
                "Take a moment to read the terminal message before you begin your journey."
        ));
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


