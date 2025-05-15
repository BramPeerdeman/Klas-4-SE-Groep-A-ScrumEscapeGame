package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

public class RoomBacklogRefinement extends RoomWithQuestion
{
    public RoomBacklogRefinement(int id)
    {
        super(id,"""
            Je duwt de stalen deur open; hij piept alsof hij al jaren
            niet meer is gesmeerd. Binnen flikkeren kapotte TL-lampen
            boven een tafel vol verroeste schroevendraaiers en half
            gesoldeerde printplaten. De lucht smaakt naar roest en
            verbrand plastic, en mistige vlekken kruipen over de vloer.
            Er klinkt haast onhoorbaar geruis – komt het uit de
            ventilatieroosters?
            """,
                RoomQuestions.getQuestionForRoom(id),
                new MultipleChoiceStrategy()
        );
    }
}



