package org.ScrumEscapeGame.Providers;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Providers.HintProvider;
import java.util.List;

public class QuestionWithHints {
    private final Question question;
    private final List<HintProvider> hintProviders;

    public QuestionWithHints(Question question, List<HintProvider> hintProviders) {
        this.question = question;
        this.hintProviders = hintProviders;
    }

    public Question getQuestion() {
        return question;
    }

    public List<HintProvider> getHintProviders() {
        return hintProviders;
    }
}
