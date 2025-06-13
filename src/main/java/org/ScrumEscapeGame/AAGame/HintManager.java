package org.ScrumEscapeGame.AAGame;

public class HintManager
{
    private int usedHints;
    private final int maxHints;

    public HintManager(int maxHints)
    {
        this.maxHints = maxHints;
        this.usedHints = 0;
    }

    public void requestHint()
    {
        if (usedHints >= maxHints)
        {
            throw new IllegalStateException("Maximaal aantal hints bereikt: " + maxHints);
        }
        usedHints++;
    }

    public int getUsedHints() {
        return usedHints;
    }

    public int getRemainingHints() {
        return maxHints - usedHints;
    }
}
