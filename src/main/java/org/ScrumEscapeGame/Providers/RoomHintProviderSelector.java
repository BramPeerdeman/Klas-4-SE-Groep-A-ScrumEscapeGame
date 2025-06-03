package org.ScrumEscapeGame.Providers;

import java.util.List;
import java.util.Random;

public class RoomHintProviderSelector implements HintProviderSelector {
    private final List<HintProvider> hintProviders;
    private final Random random = new Random();

    public RoomHintProviderSelector(List<HintProvider> hintProviders) {
        this.hintProviders = hintProviders;
    }

    @Override
    public HintProvider selectHintProvider(List<HintProvider> providers) {
        return hintProviders.get(random.nextInt(hintProviders.size()));
    }
}
