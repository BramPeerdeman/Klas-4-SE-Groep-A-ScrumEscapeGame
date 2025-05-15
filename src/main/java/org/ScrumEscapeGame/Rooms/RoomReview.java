package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

public class RoomReview extends RoomWithQuestion {
    public RoomReview(int id) {
        super(
                5,
                """
                Een laag stof bedekt een vergadertafel, waar verweerde notulen
                liggen naast omgevallen koffiekopjes. Het venijnige geruchtje
                van iets bewegends buiten bereik zweeft door de kier van de deur.
                In de spiegel aan de muur zie je vaag een tweede gestalte,
                maar als je omdraait is er niemand.
                """,
                RoomQuestions.getQuestionForRoom(id),
                new MultipleChoiceStrategy()
        );
    }
}
