package org.ScrumEscapeGame.cli;



import java.util.HashMap;
import java.util.Map;

/**
 * Verantwoordelijkheid:
 *   - Registreren van Command-instanties onder een sleutel (String)
 *   - Teruggeven van een Command op basis van de ingevoerde sleutel
 */
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Registreer een nieuw commando onder de gegeven key.
     * @param key    De string waarop de speler typt (bijv. "look", "w", "status")
     * @param command  De Command-implementatie die uitgevoerd moet worden
     */
    public void register(String key, Command command) {
        if (key == null || command == null) {
            throw new IllegalArgumentException("Key en command mogen niet null zijn");
        }
        commands.put(key.toLowerCase(), command);
    }

    /**
     * Haal het Command-object op dat bij de gegeven sleutel hoort.
     * @param key  Ingevoerde commando-string
     * @return     Het Command, of null als er geen matching key is
     */
    public Command get(String key) {
        if (key == null) return null;
        return commands.get(key.toLowerCase());
    }
}

