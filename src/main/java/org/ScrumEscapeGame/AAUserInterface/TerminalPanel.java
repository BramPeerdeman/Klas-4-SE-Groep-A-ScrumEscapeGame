package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Providers.*;
import org.ScrumEscapeGame.Rooms.BossRoom;
import org.ScrumEscapeGame.Rooms.PenultimateRoom;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Rooms.StartingRoom;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.border.TitledBorder;

/**
 * TerminalPanel now supports dynamic hints, tutorials, and motivation messages.
 * It queries the current room from the provided GameContext.
 */
public class TerminalPanel extends JPanel {

    // UI areas.
    private JTextArea hintArea;
    private JTextArea tutorialArea;
    private JTextArea motivationalArea;

    // Reference to GameContext so that we may read dynamic room data.
    private final GameContext context;

    public TerminalPanel(GameContext context, GameUIService uiService) {
        this.context = context;
        setLayout(new BorderLayout());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "toggleTerminal");
        this.getActionMap().put("toggleTerminal", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.toggleTerminalPanel();
            }
        });

        // Panel to hold all three text areas.
        JPanel messagePanel = new JPanel(new GridLayout(3, 1));

        // Hint area.
        hintArea = new JTextArea(3, 40);
        hintArea.setEditable(false);
        hintArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Hint", TitledBorder.LEFT, TitledBorder.TOP));
        messagePanel.add(hintArea);

        // Tutorial area.
        tutorialArea = new JTextArea(3, 40);
        tutorialArea.setEditable(false);
        tutorialArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Tutorial", TitledBorder.LEFT, TitledBorder.TOP));
        messagePanel.add(tutorialArea);

        // Motivational message area.
        motivationalArea = new JTextArea(3, 40);
        motivationalArea.setEditable(false);
        motivationalArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Motivatie", TitledBorder.LEFT, TitledBorder.TOP));
        messagePanel.add(motivationalArea);

        add(messagePanel, BorderLayout.CENTER);
    }

    /**
     * Activates the assistant by populating the hint, tutorial, and motivation messages.
     * If the current room supports a question helper, then a hint is selected via its HintProviderSelector.
     * Otherwise, default messages are used. The tutorial message is based on the room's display order,
     * and the motivational message is derived from the room type.
     */
    public void activateAssistant() {
        // Retrieve the current room from the game context.
        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());

        String hintMessage = "Hint: [Geen hint beschikbaar]";
        String tutorialMessage;
        String motivationalMessage;

        // If the current room is the starting room, use hardcoded (or repository-driven) messages.
        if (currentRoom instanceof StartingRoom) {
            // Since the TutorialRepository already has an entry for display order 1, this returns the starting message.
            tutorialMessage = "Tutorial: " + TutorialRepository.getTutorialMessage(currentRoom.getDisplayOrder());
            hintMessage = "Hint: Press R to open the terminal and read the introduction.";
            motivationalMessage = "Welcome! Let your adventure begin!";

        } else if (currentRoom instanceof RoomWithQuestion) {
            RoomWithQuestion roomWithQuestion = (RoomWithQuestion) currentRoom;
            QuestionWithHints qwh = roomWithQuestion.getQuestionWithHints();

            // Get only the helpful hints.
            List<HintProvider> helpHints = qwh.getHelpHintProviders();
            if (!helpHints.isEmpty()) {
                // Only select and cache a hint the first time.
                if (qwh.getCachedHelpfulHint() == null) {
                    HintProvider selected = new RoomHintProviderSelector(helpHints).selectHintProvider(helpHints);
                    qwh.cacheHelpfulHint(selected.getHint());
                }
                hintMessage = qwh.getCachedHelpfulHint();
            }

            // Get the tutorial message from the repository based on display order.
            tutorialMessage = "Tutorial: " + TutorialRepository.getTutorialMessage(currentRoom.getDisplayOrder());

            // Decide on a motivational message based on room type.
            if (currentRoom instanceof BossRoom) {
                motivationalMessage = "You're facing the Boss! Show your true skills!";
            } else if (currentRoom instanceof PenultimateRoom) {
                motivationalMessage = "Almost there! Keep pushing forward!";
            } else {
                motivationalMessage = "You're thinking as a real product owner!";
            }
        } else {
            // Fallback default messages (should rarely be reached).
            tutorialMessage = "Tutorial: Explore and learn as you go.";
            motivationalMessage = "Stay determined!";
        }

        // Update the UI components with the messages.
        hintArea.setText(hintMessage);
        tutorialArea.setText(tutorialMessage);
        motivationalArea.setText(motivationalMessage);
    }


}


