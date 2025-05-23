package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;

public class RoomSprintBacklog extends RoomWithQuestion {
    public RoomSprintBacklog(int id) {
        super(
                7,
                """
                De deur kraakt open naar een ruimte vol dossiers in plexiglas
                kasten. Elk dossier is verzegeld, maar je hoort iets zacht
                schrapen: een nagel, een klauw, gevangen achter glas.
                Plotseling valt een lade uit zichzelf open, onthullend
                een foto van … jou?
                """,
                RoomQuestions.getQuestionForRoom(id),
                new MultipleChoiceStrategy()
        );
    }
}
