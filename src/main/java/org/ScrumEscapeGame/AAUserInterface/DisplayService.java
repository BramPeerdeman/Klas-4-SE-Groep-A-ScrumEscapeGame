package org.ScrumEscapeGame.AAUserInterface;

/**
 * DisplayService abstracts the mechanisms for displaying messages and retrieving user input.
 * This allows flexibility in UI implementation, whether using console, GUI, or another system.
 */
public interface DisplayService {
    /**
     * Prints a message to the UI output.
     *
     * @param message The message to display.
     */
    void printMessage(String message);

    /**
     * Reads a line of input from the player.
     *
     * @param prompt The prompt message displayed to the player.
     * @return The player's input as a string.
     */
    String readLine(String prompt);
}


