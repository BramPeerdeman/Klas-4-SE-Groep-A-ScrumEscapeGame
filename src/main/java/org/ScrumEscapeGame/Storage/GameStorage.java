package org.ScrumEscapeGame.Storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ScrumEscapeGame.GameObjects.GameState;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Player;

import java.io.*;
import java.util.List;

public class GameStorage {
    private static final String SAVE_FILE = "savegame.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveGame(Player player) {
        List<String> inventoryItems = player.getInventory().getItems();
        GameState state = new GameState(
                player.getPosition(),
                player.getSolvedRooms(),
                inventoryItems
        );

        try (Writer writer = new FileWriter(SAVE_FILE)) {
            gson.toJson(state, writer);
            System.out.println("✅ Progress saved!");
        } catch (IOException e) {
            System.out.println("❌ Failed to save game: " + e.getMessage());
        }
    }

    public static void loadGame(Player player) {
        try (Reader reader = new FileReader(SAVE_FILE)) {
            GameState state = gson.fromJson(reader, GameState.class);
            if (state != null) {
                player.setPosition(state.getPlayerPosition());

                // Set solved rooms using logic-aware method
                List<Integer> solvedRooms = state.getSolvedRooms();
                if (solvedRooms != null) {
                    for (int roomId : solvedRooms) {
                        player.addSolvedRoom(roomId); // Ensures observers or unlocks are triggered
                    }
                }

                // Restore inventory
                if (state.getInventoryItems() != null) {
                    player.getInventory().setItems(state.getInventoryItems());
                }

                System.out.println("✅ Game loaded!");
            }
        } catch (IOException e) {
            System.out.println("⚠️ No saved game found.");
        }
    }

}
