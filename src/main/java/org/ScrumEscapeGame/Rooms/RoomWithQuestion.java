package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.*;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Monster;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;
import org.ScrumEscapeGame.Monster.MonsterManager;
import org.ScrumEscapeGame.Monster.StatueMonster;
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
    private boolean hasHelper;
    private boolean hasStatue;

    // New fields for tracking room clearance.
    // Indicates if the challenge (i.e. the question) has been solved.
    private boolean challengeCleared = false;
    // Reference to the currently active monster in the room, if any.
    private Monster activeMonster = null;


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
     * @param questionWithHints    the question or challenge associated with the room.
     * @param strategy    the strategy to use when asking the question.
     * @param hintSelector what type of hint you're getting.
     * @param hasHelper whether it can use hints.
     */
    public RoomWithQuestion(int id, String description, QuestionWithHints questionWithHints,
                            QuestionStrategy strategy, HintProviderSelector hintSelector,
                            Boolean hasHelper, Boolean hasStatue) {
        super(id, description);
        this.questionWithHints = questionWithHints;
        this.strategy = strategy;
        this.hintSelector = hintSelector;
        this.hasHelper = hasHelper;
        this.hasStatue = hasStatue;
    }

    /**
     * Returns true if the room can be left.
     * The player is not allowed to leave until the challenge is cleared,
     * which means either answering the question correctly or defeating any active monster.
     */
    public boolean canLeave() {
        // If there's an active monster and it's still alive, the room cannot be left.
        if (activeMonster != null && activeMonster.isAlive()) {
            return false;
        }
        return true;
    }

    // Setter for challengeCleared so it can be set, for example, when the question is answered correctly.
    public void setChallengeCleared(boolean cleared) {
        this.challengeCleared = cleared;
    }

    // Setter for the active monster (for example, when the statue awakens)
    public void setActiveMonster(Monster monster) {
        this.activeMonster = monster;
    }

    // Optionally, a getter to check if there is an active monster.
    public Monster getActiveMonster() {
        return activeMonster;
    }

    // ... (other methods such as onEnter, triggerQuestion, giveHint remain unchanged)



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
        if (hasHelper) {
            publisher.publish(new NotificationEvent("A terminal is located in this room, it's screen reads 'press R' "));
        }
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
    public void triggerQuestion(Player player, EventPublisher<GameEvent> publisher, GameUIService displayService) {
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
                // If a monster is active, force it to die
                if (activeMonster != null && activeMonster.isAlive()) {
                    activeMonster.die();  // Ensure your monster implementation properly marks it as dead.
                    setActiveMonster(null);
                }
                publisher.publish(new DoorUnlockedEvent(sharedDoor));
            } else {
                // 1st incorrect answer → show hint
                if (hasHelper) {
                    String randomHint = hintSelector.selectHintProvider(questionWithHints.getHintProviders()).getHint();
                    publisher.publish(new NotificationEvent("Here's a hint to help you: " + randomHint));
                }

                // Retry the same question once
                boolean retryCorrect = strategy.ask(player, questionWithHints.getQuestion(), publisher, displayService);
                if (retryCorrect) {
                    player.addSolvedRoom(getId());
                    publisher.publish(new DoorUnlockedEvent(sharedDoor));
                } else {
                    // Instead of triggering GameResetEvent immediately, we spawn the monster.
                    publisher.publish(new NotificationEvent("Incorrect again! A monster awakens..."));

                    if (hasStatue) {
                        try {
                            StatueMonster monster = new StatueMonster("Backlog Beast",
                                    "The statue awakens with a menacing glare.",
                                    publisher,
                                    /* gameContext */ null,
                                    5); // For example, 5 hitpoints
                            monster.spawn();
                            // Register the monster with the Monster Manager.
                            MonsterManager.getInstance().registerActiveMonster(monster);
                            // Also, store the monster in this room.
                            setActiveMonster(monster);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }



    public boolean hasHelper() {
        return hasHelper;
    }

    public void giveHint(EventPublisher<GameEvent> publisher) {
        String randomHint = hintSelector.selectHintProvider(questionWithHints.getHintProviders()).getHint();
        publisher.publish(new NotificationEvent("Here's a hint to help you: " + randomHint));
    }

    public QuestionWithHints getQuestionWithHints() {
        return questionWithHints;
    }

    public HintProviderSelector getHintProviderSelector() {
        return hintSelector;
    }

    public boolean hasActiveMonster() {
        return activeMonster != null && activeMonster.isAlive();
    }

    // New helper method: deactivates any active monster in the room.
    public void clearActiveMonster() {
        this.activeMonster = null;
    }


}







