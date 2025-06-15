package org.ScrumEscapeGame.Providers;

import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Providers.HintProvider;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionWithHints {
    private final Question question;
    private final List<HintProvider> hintProviders;
    private String cachedHelpfulHint; // This will hold the helpful hint once selected.

    public QuestionWithHints(Question question, List<HintProvider> hintProviders) {
        this.question = question;
        this.hintProviders = hintProviders;
    }

    /**
     * Retrieves only the helpful hint providers.
     */
    public List<HintProvider> getHelpHintProviders() {
        return hintProviders.stream()
                .filter(h -> h instanceof HelpHintProvider)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves only the funny hint providers.
     */
    public List<HintProvider> getFunnyHintProviders() {
        return hintProviders.stream()
                .filter(h -> h instanceof FunnyHintProvider)
                .collect(Collectors.toList());
    }

    public String getCachedHelpfulHint() {
        return cachedHelpfulHint;
    }

    public void cacheHelpfulHint(String hint) {
        this.cachedHelpfulHint = hint;
    }

    public Question getQuestion() {
        return question;
    }

    public List<HintProvider> getHintProviders() {
        return hintProviders;
    }
}
