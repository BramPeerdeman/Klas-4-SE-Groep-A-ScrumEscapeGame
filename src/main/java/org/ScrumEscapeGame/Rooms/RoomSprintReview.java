package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

public class RoomSprintReview extends RoomWithQuestion {
    public RoomSprintReview(int id) {
        super(
                9,
                """
                Zware deuren schuiven opzij naar een ruimte vol serverracks,
                hun ventilatoren dreunen als beestachtige longen.
                Wazige rode lampjes knipperen in ritmische pulsen,
                alsof ze je hartslag bijhouden. Elke seconde voel je de
                spanning groeien – hier wil je niet langer blijven.
                """,
                RoomQuestions.getQuestionForRoom(id),
                new MultipleChoiceStrategy()
        );
    }
}
