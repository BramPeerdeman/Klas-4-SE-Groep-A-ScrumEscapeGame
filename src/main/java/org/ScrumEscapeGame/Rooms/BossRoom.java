package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.GameResetEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.AAUserInterface.GameUIService;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Joker;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;

import java.util.Collections;
import java.util.List;

/**
 * BossRoom represents a room where the final challenge is presented.
 * It contains a specialized boss door that requires keys to unlock.
 */
public class BossRoom extends Room implements HasQuestions {
    // The locked door that guards the boss challenge.
    private LockedDoor bossDoor;
    private static final boolean DEBUG = true;

    // List of boss-level questions from RoomQuestions.getBossQuestions().
    private final List<Question> questions = RoomQuestions.getBossQuestions();

    // The strategy used to ask questions (e.g., MultipleChoiceStrategy).
    private final QuestionStrategy strategy;

    // Tracks how many questions have been answered correctly.
    private int questionsAnsweredCount = 0;

    // Flag that indicates a failure (e.g., a wrong answer) has occurred.
    private boolean failed = false;

    // In boss room we don't offer any jokers.
    @Override
    public List<Joker> getAvailableJokers() {
        return Collections.emptyList();
    }

    /**
     * Constructs a BossRoom with a given id, description, boss door, and question strategy.
     *
     * @param id          the unique room identifier.
     * @param description a description of the room.
     * @param bossDoor    the locked door instance guarding the boss challenge.
     * @param strategy    the question strategy to use.
     */
    public BossRoom(int id, String description, LockedDoor bossDoor, QuestionStrategy strategy) {
        super(id, description);
        this.bossDoor = bossDoor;
        this.strategy = strategy;
    }

    /**
     * Returns the boss door associated with this room.
     *
     * @return the LockedDoor instance for the boss room.
     */
    public LockedDoor getBossDoor() {
        return bossDoor;
    }

    /**
     * Returns the list of boss questions.
     *
     * @return a list of questions.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Returns the number of correctly answered boss questions.
     *
     * @return the count of answered questions.
     */
    public int getQuestionsAnsweredCount() {
        return questionsAnsweredCount;
    }

    /**
     * Increments the count of correctly answered boss questions by one.
     */
    public void incrementQuestionsAnsweredCount() {
        questionsAnsweredCount++;
    }

    /**
     * Sets the failed flag.
     *
     * @param failed true if the challenge is failed, false otherwise.
     */
    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    /**
     * Returns the current failed state.
     *
     * @return true if a wrong answer was given, false otherwise.
     */
    public boolean isFailed() {
        return failed;
    }

    /**
     * Called when the player enters the boss room.
     * In addition to the standard room entry logic, the player is informed that keys are needed.
     *
     * @param player    the player entering the room.
     * @param publisher the event publisher for sending notifications.
     */
    @Override
    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        // Let the base room logic update player position etc.
        super.onEnter(player, publisher);
        // Notify the player specific to the boss room.
        publisher.publish(new NotificationEvent("You have entered the boss room. To unlock the door, you must use your keys."));
    }

    /**
     * Triggers the boss challenge question.
     * The player must answer three questions correctly.
     * A wrong answer immediately marks the challenge as failed.
     *
     * @param player         the current player.
     * @param publisher      the event publisher.
     * @param displayService the display service for showing messages.
     */
    public void triggerQuestion(Player player,
                                EventPublisher<GameEvent> publisher,
                                GameUIService displayService) {
        if (DEBUG) {
            System.out.println("DEBUG: triggerQuestion() called in BossRoom id: " + getId());
            System.out.println("DEBUG: Questions answered = " + questionsAnsweredCount + ", failed = " + failed);
        }

        // If failure has already occurred, do nothing further.
        if (failed) {
            return;
        }

        // Only ask questions if fewer than 3 have been answered correctly.
        if (questionsAnsweredCount < 3) {
            Question currentQ = questions.get(questionsAnsweredCount);
            boolean correct = strategy.ask(player, currentQ, publisher, displayService);

            if (DEBUG) {
                System.out.println("DEBUG: Boss question #" + (questionsAnsweredCount + 1) +
                        " answered. Correct? " + correct);
            }
            if (!correct) {
                // On a wrong answer, mark failure and publish game reset event.
                publisher.publish(new GameResetEvent("Wrong answer in boss room, game resetting...."));
                return;
            }
            // Correct answer; increment the count.
            incrementQuestionsAnsweredCount();

            // If three correct answers have been provided, announce victory.
            if (questionsAnsweredCount == 3) {
                publisher.publish(new NotificationEvent(
                        "Congratulations, you answered all boss questions correctly, you won!"
                ));
            }
        }
    }
}




