package org.ScrumEscapeGame.Providers;

import java.util.List;
import java.util.Random;

public class RandomHintProviderSelector implements HintProviderSelector {
    private final List<HintProvider> providers;
    private final Random random = new Random();

    public RandomHintProviderSelector(List<HintProvider> providers) {
        this.providers = providers;
    }

    @Override
    public HintProvider selectHintProvider() {
        return providers.get(random.nextInt(providers.size()));
    }
}

