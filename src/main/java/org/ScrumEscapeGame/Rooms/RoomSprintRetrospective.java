package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

public class RoomSprintRetrospective extends RoomWithQuestion {
    public RoomSprintRetrospective(int id) {
        super(
                8,
                """
                Een dikke mist kronkelt over de vloer. Aan de muren hangen
                bordjes met cryptische aantekeningen in bloedrode inkt.
                Er ligt een verzameling kapotgeslagen projectieschermen
                op de grond. Iets fluistert iets onverstaanbaars,
                net buiten gehoorsafstand.
                """,
                RoomQuestions.getQuestionForRoom(id),
                new MultipleChoiceStrategy()
        );
    }
}
