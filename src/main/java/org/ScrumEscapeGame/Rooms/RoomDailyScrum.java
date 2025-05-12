package org.ScrumEscapeGame.Rooms;

import org.ScrumEscapeGame.GameObjects.Room;

public class RoomDailyScrum extends RoomWithQuestion {
    public RoomDailyScrum() {
        super(
                2,
                """
                De volgende deur valt met een klap dicht. Dit kamertje is
                nauwer: muren van ruw beton druppen water, en elektrische
                kabels kronkelen als walgelijke aderen over de grond.
                In de hoek knippert een oud computerscherm, het flauwse licht
                werpt lange schaduwen. Je voelt dat je niet alleen bent.
                """,
                RoomQuestions.getQuestionForRoom(2)
        );
    }
}
