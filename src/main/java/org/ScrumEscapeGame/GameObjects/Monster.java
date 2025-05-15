package org.ScrumEscapeGame.GameObjects;

public class Monster {
    private String name;
    private String description;
    private boolean alive;
    private boolean boss;
    private final int    requiredCorrect; // hoeveel juiste antwoorden bij boss
    private int correctCount = 0; // teller hoeveel boss vragen je goed heb
    private int roomId;


    //dit is een NORMAAL monster
    public Monster(String name, String description) {
        this(name, description, false, 1);
    }


    // dit is een BOSS MONSTER je moet zoveel goed hebben als de waarde van requiredCorrect
    public Monster(String name, String description, int requiredCorrect) {
        this(name, description, true, requiredCorrect);
    }

    private Monster(String name, String description, boolean boss, int requiredCorrect) {
        this.name            = name;
        this.description     = description;
        this.boss            = boss;
        this.requiredCorrect = requiredCorrect;
        this.alive           = false;
    }
}
