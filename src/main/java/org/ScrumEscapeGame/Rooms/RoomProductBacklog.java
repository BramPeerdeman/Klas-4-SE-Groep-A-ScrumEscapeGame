package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

public class RoomProductBacklog extends RoomWithQuestion {
    public RoomProductBacklog() {
        super(
                4,
                """
                De kamer is gevuld met stapels lijsten en gekleurde post-its,
                maar alle tekst is uitgewist door schimmel. Bakens van rottende
                documenten vullen de ruimte met een zuurzoete geur. Plotseling
                kraakt iets achter een van de planken – een echo die niet
                van deze wereld lijkt.
                """,
                RoomQuestions.getQuestionForRoom(4)
        );
    }
}
