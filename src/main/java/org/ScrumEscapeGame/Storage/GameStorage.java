package org.ScrumEscapeGame.Storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ScrumEscapeGame.GameObjects.GameState;

import java.io.*;
import java.util.List;

public class GameStorage {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveGame(String fileName, int playerPosition, List<Integer> solvedRooms) {
        GameState gameState = new GameState(playerPosition, solvedRooms);
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(gameState, writer);
            System.out.println("✅ Game saved.");
        } catch (IOException e) {
            System.out.println("❌ Failed to save game: " + e.getMessage());
        }
    }

    public GameState loadGame(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            GameState gameState = gson.fromJson(reader, GameState.class);
            System.out.println("✅ Game loaded.");
            return gameState;
        } catch (IOException e) {
            System.out.println("⚠️ No previous save found.");
            return null;
        }
    }
}
