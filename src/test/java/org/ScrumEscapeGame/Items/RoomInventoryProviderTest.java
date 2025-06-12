package org.ScrumEscapeGame.Items;

import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Question;
import org.ScrumEscapeGame.Providers.HintProviderSelector;
import org.ScrumEscapeGame.Providers.QuestionWithHints;
import org.ScrumEscapeGame.Providers.RoomHintProviderSelector;
import org.ScrumEscapeGame.Rooms.RoomQuestions;
import org.ScrumEscapeGame.Rooms.RoomWithPresetItems;
import org.ScrumEscapeGame.Rooms.RoomWithQuestion;
import org.ScrumEscapeGame.Strategy.MultipleChoiceStrategy;
import org.ScrumEscapeGame.Strategy.QuestionStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

// Dummy implementations for testing purposes

// A simple dummy room that supports preset items.
class DummyPresetRoom extends RoomWithPresetItems {
    public DummyPresetRoom(int id, String description) {
        super(id, description);
    }
}

// A simple dummy room that does not have preset items.
class DummyGenericRoom extends RoomWithQuestion {
    public DummyGenericRoom(int id, String description, QuestionWithHints question, QuestionStrategy strategy, HintProviderSelector hintProviderSelector) {
        super(id, description, question, strategy, hintProviderSelector, true);
    }
}

public class RoomInventoryProviderTest {

    private RoomInventoryProvider inventoryProvider;

    @BeforeEach
    public void setUp() {
        // Initialize the RoomInventoryProvider before each test.
        inventoryProvider = new RoomInventoryProvider();
    }

    @Test
    public void testGetInventoryForPresetRoom() {
        // Create a dummy room which is a RoomWithPresetItems.
        DummyPresetRoom presetRoom = new DummyPresetRoom(1, "Preset room description");

        // Get the inventory for the room.
        Inventory inventory = inventoryProvider.getInventoryFor(presetRoom);

        // Assert that the correct type of inventory is returned.
        // (Assuming your PresetInventory extends Inventory.)
        assertTrue(inventory instanceof PresetInventory, "Expected a PresetInventory for a preset room.");

        // Check that the inventory has the hardcoded preset items.
        List<Item> items = inventory.getItems();
        assertFalse(items.isEmpty(), "Expected preset items to be added to the preset room.");

        // Optionally, check if one of the items is the expected special item.
        boolean hasSpecialKey = items.stream().anyMatch(i -> i.getName().equalsIgnoreCase("Special Key"));
        assertTrue(hasSpecialKey, "Expected a 'Special Key' in the preset inventory.");
    }

    @Test
    public void testGetInventoryForGenericRoom() {
        // Create a generic room.
        DummyGenericRoom genericRoom = new DummyGenericRoom(2, "Generic room description", RoomQuestions.getQuestionForRoom(1), new MultipleChoiceStrategy(),
                new RoomHintProviderSelector(RoomQuestions.getQuestionForRoom(2).getHintProviders()));

        // Get the inventory for the generic room.
        Inventory inventory = inventoryProvider.getInventoryFor(genericRoom);

        // Assert that it returns a BasicInventory.
        assertTrue(inventory instanceof BasicInventory, "Expected a BasicInventory for a generic room.");

        // Since the items are added randomly, we may have 0 or more items.
        // Check that the items list is not null.
        assertNotNull(inventory.getItems(), "The inventory items list should not be null.");
    }
}
