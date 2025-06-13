package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.AAGame.HintManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HintManagerTest {

    private HintManager hintManager;

    @BeforeEach
    void setUp() {
        hintManager = new HintManager(3); // maximaal 3 hints toegestaan
    }

    @Test
    void testInitialHints() {
        assertEquals(0, hintManager.getUsedHints());
        assertEquals(3, hintManager.getRemainingHints());
    }

    @Test
    void testHintsWithinLimit() {
        hintManager.requestHint();
        hintManager.requestHint();
        assertEquals(2, hintManager.getUsedHints());
        assertEquals(1, hintManager.getRemainingHints());
    }

    @Test
    void testHintsAtLimit() {
        hintManager.requestHint();
        hintManager.requestHint();
        hintManager.requestHint();
        assertEquals(3, hintManager.getUsedHints());
        assertEquals(0, hintManager.getRemainingHints());
    }

    @Test
    void testExceedingHintLimit() {
        hintManager.requestHint();
        hintManager.requestHint();
        hintManager.requestHint();

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            hintManager.requestHint();
        });
        assertEquals("Maximaal aantal hints bereikt: 3", exception.getMessage());
    }
}
