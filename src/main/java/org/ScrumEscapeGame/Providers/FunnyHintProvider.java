package org.ScrumEscapeGame.Providers;

public class FunnyHintProvider implements HintProvider {
    private final String hint;

    public FunnyHintProvider(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return "😜 " + hint;
    }
}
