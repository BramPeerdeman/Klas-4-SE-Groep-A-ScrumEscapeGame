package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

import java.util.List;

public class RoomProductBacklog extends RoomWithQuestion {

    static {
        RoomQuestions.registerQuestion(4, new Question(
                "Wat is het doel van de Product Backlog?",
                List.of(
                        "Een plaats voor projectdocumentatie",
                        "Een overzicht van alle bugs",
                        "Een overzicht van werk dat nodig is",
                        "Een lijst met medewerkers"
                ),
                "Een overzicht van werk dat nodig is"
        ));
    }

    public RoomProductBacklog(int id) {
        super(
                id,
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
