package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;
import org.ScrumEscapeGame.Observer.Observer;
import org.ScrumEscapeGame.Observer.Subject;
import org.ScrumEscapeGame.Providers.HintProviderSelector;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;
import org.ScrumEscapeGame.AAGame.Game;

import java.util.Collections;
import java.util.List;

import org.ScrumEscapeGame.AAGame.GameCycleManager;

// RoomWithQuestion.java
import java.util.ArrayList;

/**
 * A room that presents a question or challenge to the player.
 * When the room is entered, if the question has not been asked,
 * it prompts the player to attempt the challenge. If answered correctly,
 * the associated door is unlocked.
 */
public class RoomWithQuestion extends Room implements HasQuestions {
    // The question to be presented.
    private QuestionWithHints questionWithHints;
    // The strategy used to ask the question (e.g., MultipleChoiceStrategy).
    private QuestionStrategy strategy;
    // Tracks whether the question has been asked already to prevent repeat prompting.
    private boolean questionAsked = false;
    // Holds the door associated with the room (shared between connected rooms).
    private LockedDoor sharedDoor;
    // Flag for debugging purposes.
    private static final boolean DEBUG = true;
    private final HintProviderSelector hintSelector;


    // DIT MOET NOG WORDEN AANGEPAST ALS JE JOKERS IN DE ROOM WIL
    @Override
    public List<Joker> getAvailableJokers() {
        return Collections.emptyList();
    }

    /**
     * Constructs a new RoomWithQuestion.
     *
     * @param id          the room identifier.
     * @param description a description of the room.
     * @param question    the question or challenge associated with the room.
     * @param strategy    the strategy to use when asking the question.
     */
    public RoomWithQuestion(int id, String description, QuestionWithHints questionWithHints, QuestionStrategy strategy, HintProviderSelector hintSelector) {
        super(id, description);
        this.questionWithHints = questionWithHints;
        this.strategy = strategy;
        this.hintSelector = hintSelector;
    }

    /**
     * Sets the locked door associated with this room.
     *
     * @param door the LockedDoor to associate.
     */
    public void setAssociatedDoor(LockedDoor door) {
        this.sharedDoor = door;
    }

    /**
     * Invoked when the player enters the room.
     * First calls the superclass onEnter to update the player's position,
     * then publishes a notification if a challenge is available and has not yet been triggered.
     */
    @Override
    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        super.onEnter(player, publisher);
        if (questionWithHints != null && strategy != null && !questionAsked) {
            publisher.publish(new NotificationEvent("A challenge awaits in this room. Enter 'Q' to attempt the question."));
        }
    }

    /**
     * Triggers the room's question using the specified strategy.
     * If the player answers correctly, the room records the success
     * and publishes an event to unlock the associated door. Otherwise,
     * the game resets.
     *
     * @param player          the current player.
     * @param publisher       the publisher for game events.
     * @param displayService  the service used to display messages.
     */
    public void triggerQuestion(Player player, EventPublisher<GameEvent> publisher, DisplayService displayService) {
        if (DEBUG) {
            System.out.println("DEBUG: triggerQuestion() called in RoomWithQuestion id: " + getId());
        }

        if (questionWithHints != null && strategy != null && !questionAsked) {
            boolean correct = strategy.ask(player, questionWithHints.getQuestion(), publisher, displayService);
            questionAsked = true;

            if (DEBUG) {
                System.out.println("DEBUG: Question answered. Was the answer correct? " + correct);
            }

            if (correct) {
                player.addSolvedRoom(getId());
                publisher.publish(new DoorUnlockedEvent(sharedDoor));
            } else {
                // 1st incorrect answer → show hint
                String randomHint = hintSelector.selectHintProvider(questionWithHints.getHintProviders()).getHint();
                publisher.publish(new NotificationEvent("Here's a hint to help you: " + randomHint));

                // Retry the same question once
                boolean retryCorrect = strategy.ask(player, questionWithHints.getQuestion(), publisher, displayService);

                if (retryCorrect) {
                    player.addSolvedRoom(getId());
                    publisher.publish(new DoorUnlockedEvent(sharedDoor));
                } else {
                    publisher.publish(new GameResetEvent("Still incorrect! Restarting the game."));
                }
            }
        }
    }

}







