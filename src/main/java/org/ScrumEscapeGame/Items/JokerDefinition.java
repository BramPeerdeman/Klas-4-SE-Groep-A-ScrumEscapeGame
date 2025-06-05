package org.ScrumEscapeGame.Items;



/**
 * Een simpele “configuratie” voor één Joker‐type.
 * - jokerType: de naam (of het enum‐key) van de subklasse
 * - itemId:   het integer‐ID dat je straks in de constructor van KeyJoker of JokerHint meegeeft
 * - name:     de display‐naam
 * - description: de beschrijving die je in die constructor doorgeeft
 */
public class JokerDefinition {
    private final String jokerType;
    private final int itemId;
    private final String name;
    private final String description;

    public JokerDefinition(String jokerType, int itemId, String name, String description) {
        this.jokerType = jokerType;
        this.itemId = itemId;
        this.name = name;
        this.description = description;
    }

    public String getJokerType() { return jokerType; }
    public int getItemId()       { return itemId; }
    public String getName()      { return name; }
    public String getDescription() { return description; }
}
