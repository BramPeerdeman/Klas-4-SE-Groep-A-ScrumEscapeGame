package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAEvents.GameResetEvent;
import org.ScrumEscapeGame.AAEvents.NotificationEvent;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
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
    // Drie “boss‐level” vragen uit RoomQuestions.getBossQuestions()


    private final List<Question> questions = RoomQuestions.getBossQuestions();

    // Welke strategie we gebruiken om vragen te stellen (bijv. MultipleChoiceStrategy).
    private final QuestionStrategy strategy;

    // Houdt bij hoeveel vragen al correct zijn beantwoord.
    private int questionsAnsweredCount = 0;

    // Als de speler één vraag fout beantwoordt, zetten we deze flag en stoppen we verdere checks.
    private boolean failed = false;

    // Dit zo laten als je geen jokers in boss room wil
    @Override
    public List<Joker> getAvailableJokers() {
        return Collections.emptyList();
    }




    /**
     * Constructs a BossRoom with a given id, description, and boss door.
     *
     * @param id          the unique room identifier.
     * @param description a description of the room.
     * @param bossDoor    the locked door instance guarding the boss challenge.
     */
    public BossRoom(int id, String description, LockedDoor bossDoor, QuestionStrategy strategy) {
        super(id, description);
        this.bossDoor = bossDoor;
        this.strategy = strategy;

    }

    /**
     * Returns the boss door associated with this room.
     *
     * @return the LockedDoor instance for this boss room.
     */
    public LockedDoor getBossDoor() {
        return bossDoor;
    }

    /**
     * Called when the player enters the boss room.
     * In addition to the standard room entry logic, it publishes a notification
     * informing the player that the door requires keys to unlock.
     *
     * @param player    the player entering the room.
     * @param publisher the event publisher for sending notifications.
     */
    @Override
    public void onEnter(Player player, EventPublisher<GameEvent> publisher) {
        // Let the base class update the player's position and notify entry.
        super.onEnter(player, publisher);

        // Inform the player specific to the boss room.
        publisher.publish(new NotificationEvent("You have entered the boss room. To unlock the door, you must use your keys."));
    }
    public void triggerQuestion(Player player,
                                EventPublisher<GameEvent> publisher,
                                DisplayService displayService) {
        if (DEBUG) {
            System.out.println("DEBUG: triggerQuestion() called in BossRoom id: " + getId());
            System.out.println("DEBUG: vragen beantwoord = " + questionsAnsweredCount + ", failed = " + failed);
        }

        // Als er al eerder een fout was, doen we niets meer.
        if (failed) {
            return;
        }

        // Zolang er nog minder dan 3 correct beantwoorde vragen zijn:
        if (questionsAnsweredCount < 3) {
            Question currentQ = questions.get(questionsAnsweredCount);
            boolean correct = strategy.ask(player, currentQ, publisher, displayService);

            if (DEBUG) {
                System.out.println("DEBUG: Boss vraag #" + (questionsAnsweredCount + 1) +
                        " beantwoord. Correct? " + correct);
            }

            if (!correct) {
                // Bij fout antwoord: direct game reset.
                failed = true;
                publisher.publish(new GameResetEvent("Wrong answer in boss room, game resetting...."));
                return;
            }

            // Als correct, verhogen we het aantal correct beantwoorde vragen.
            questionsAnsweredCount++;

            // Als dit nu de derde correcte vraag is, beëindig het spel.
            if (questionsAnsweredCount == 3) {
                publisher.publish(new NotificationEvent(
                        "Congratulations, you answered all questions correctly, you won!"
                ));
            }
        }
    }
}


