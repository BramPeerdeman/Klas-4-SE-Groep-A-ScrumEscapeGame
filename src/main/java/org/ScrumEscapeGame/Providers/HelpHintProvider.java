package org.ScrumEscapeGame.Providers;

public class HelpHintProvider implements HintProvider
{
    private final String hint;

    public HelpHintProvider(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return "💡 " + hint;
    }
}
