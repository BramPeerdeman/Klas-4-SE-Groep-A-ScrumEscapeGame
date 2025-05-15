package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

public class RoomSprint extends RoomWithQuestion {
    public RoomSprint(int id) {
        super(
                6,
                """
                Een strakke metalen trap leidt neer in een kale kelderruimte.
                Overal liggen kapotte uurwerkonderdelen en klokken die stil zijn
                blijven staan op exact middernacht. Het echoot onder je voeten,
                en in de verte klinkt het getik van een klok die niet bestaat.
                """,
                RoomQuestions.getQuestionForRoom(id),
                new MultipleChoiceStrategy()

        );
    }
}
