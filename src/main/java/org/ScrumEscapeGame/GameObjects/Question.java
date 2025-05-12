package org.ScrumEscapeGame.GameObjects;

public class Question {
    private final String prompt;
    private final String answer;

    public Question(String prompt, String answer) {
        this.prompt = prompt;
        this.answer = answer;
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean isCorrect(String userAnswer) {
        return answer.equalsIgnoreCase(userAnswer.trim());
    }
}
