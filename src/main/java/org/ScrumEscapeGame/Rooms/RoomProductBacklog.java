package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

public class RoomProductBacklog extends RoomWithQuestion {
    public RoomProductBacklog(int id) {
        super(
                4,
                """
                De kamer is gevuld met stapels lijsten en gekleurde post-its,
                maar alle tekst is uitgewist door schimmel. Bakens van rottende
                documenten vullen de ruimte met een zuurzoete geur. Plotseling
                kraakt iets achter een van de planken – een echo die niet
                van deze wereld lijkt.
                """,
                RoomQuestions.getQuestionForRoom(id),
                new MultipleChoiceStrategy()
        );
    }
}
