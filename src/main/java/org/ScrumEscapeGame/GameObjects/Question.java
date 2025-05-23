package org.ScrumEscapeGame.GameObjects;

import java.util.List;

public class Question {
    private String prompt;
    private List<String> choices;
    private String correctAnswer;

    public Question(String prompt, List<String> choices, String correctAnswer) {
        this.prompt = prompt;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect(String userInputLetter) {
        int index = userInputLetter.toLowerCase().charAt(0) - 'a';
        if (index >= 0 && index < choices.size()) {
            return choices.get(index).equalsIgnoreCase(correctAnswer);
        }
        return false;
    }

}
