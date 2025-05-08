package org.ScrumEscapeGame.Storage;

import java.io.*;

public class GameStorage
{
    String cache;
    String test = "test";
    public void saveGame(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(test + "\n"); //Copy en paste deze line met een nieuwe getter van informatie (Bijv Kamer.getOpgelost)
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public void loadGame(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String name = reader.readLine();
            int level = Integer.parseInt(reader.readLine());
            int score = Integer.parseInt(reader.readLine());
            System.out.println("Game loaded successfully!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }
}