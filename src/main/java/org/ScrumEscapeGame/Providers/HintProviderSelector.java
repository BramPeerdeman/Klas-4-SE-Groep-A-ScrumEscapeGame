package org.ScrumEscapeGame.Providers;

import java.util.List;

public interface HintProviderSelector
{
    HintProvider selectHintProvider(List<HintProvider> providers);
}
