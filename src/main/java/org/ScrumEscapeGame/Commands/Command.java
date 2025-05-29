package org.ScrumEscapeGame.Commands;

/**
 * Represents a game command.
 * All commands in the game must provide an execute method.
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute(String args);
}

