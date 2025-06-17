package org.ScrumEscapeGame.Strategy;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAEvents.GameEvent;
import org.ScrumEscapeGame.AAUserInterface.DisplayService;
import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Question;

import java.util.*;

public class MatchingStrategy implements QuestionStrategy {
    @Override
    public boolean ask(Player player, Question question, EventPublisher<GameEvent> publisher, DisplayService displayService) {
        displayService.printMessage("🧩 Match each Scrum term with its correct definition.");

        List<String> choices = question.getChoices(); // Expected to be alternating term and definition or term list only
        if (choices == null || choices.size() % 2 != 0) {
            displayService.printMessage("Invalid matching question format.");
            return false;
        }

        int pairCount = choices.size() / 2;
        List<String> terms = choices.subList(0, pairCount);
        List<String> definitions = choices.subList(pairCount, choices.size());

        // Shuffle definitions
        List<String> shuffledDefs = new ArrayList<>(definitions);
        Collections.shuffle(shuffledDefs);

        // Display terms
        displayService.printMessage("Match the terms to the definitions by entering the correct definition number for each term.");
        for (int i = 0; i < terms.size(); i++) {
            displayService.printMessage((i + 1) + ". " + terms.get(i));
        }

        displayService.printMessage("\nDefinitions:");
        for (int i = 0; i < shuffledDefs.size(); i++) {
            displayService.printMessage("  " + (char) ('A' + i) + ") " + shuffledDefs.get(i));
        }

        // Collect matches
        List<Integer> userMatchIndices = new ArrayList<>();
        for (int i = 0; i < terms.size(); i++) {
            String input = displayService.readLine("Enter the letter for definition matching term " + (i + 1) + ": ");
            if (input == null || input.trim().isEmpty()) {
                displayService.printMessage("❌ No input provided.");
                return false;
            }
            char letter = Character.toUpperCase(input.trim().charAt(0));
            int defIndex = letter - 'A';
            if (defIndex < 0 || defIndex >= shuffledDefs.size()) {
                displayService.printMessage("❌ Invalid letter.");
                return false;
            }
            userMatchIndices.add(defIndex);
        }

        // Compute correct answer indices using the original definitions
        List<Integer> correctMatchIndices = new ArrayList<>();
        for (String def : definitions) {
            correctMatchIndices.add(shuffledDefs.indexOf(def));
        }

        if (userMatchIndices.equals(correctMatchIndices)) {
            displayService.printMessage("✅ All matches correct!");
            return true;
        } else {
            displayService.printMessage("❌ Some matches are incorrect.");
            return false;
        }
    }
}
