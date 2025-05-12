package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

public class RoomPlanning extends RoomWithQuestion {
    public RoomPlanning() {
        super(
                3,
                """
                Een ijzige koude luchtstroom slaat je tegemoet wanneer je de
                deur opent. Planken vol oude dossiermappen kreunen in het
                halfduister. Een half opgebrande kaars op een bureau flakkert
                onregelmatig en onthult geschreven krabbels over experimenten.
                Het gezoem van een ventilator klinkt als een rochelende worst.
                """,
                RoomQuestions.getQuestionForRoom(3)
        );
    }
}
