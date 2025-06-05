package org.ScrumEscapeGame.GameObjects;

import org.ScrumEscapeGame.AAEvents.EventPublisher;
import org.ScrumEscapeGame.AAGame.GameContext;

/**
 * Abstracte basis‐klasse voor alle monsters in ScrumEscapeGame.
 *
 * We gebruiken hier het Template Method‐patroon:
 *   - De method 'engage()' beschrijft de vaste volgorde waarbinnen ieder monster “leeft” en werkt.
 *   - Concrete monsters implementeren de abstracte hooks (spawn, takeTurn, die).
 *
 * Op deze manier heb je één plek waar de flow vastligt, en laat je subklassen alléén
 * de eigen details invullen.
 */
public abstract class Monster {
    // --- Gemeenschappelijke velden voor ieder monster ---
    protected final String name;
    protected final String description;
    protected boolean alive;                           // staat dit monster nog “in leven”?
    protected final EventPublisher<?> publisher;        // voor eventuele events / UI‐meldingen
    protected final GameContext context;               // verwijzing naar de spel‐context

    /**
     * Constructor: vul de naam, beschrijving, publisher en context in.
     * (We zetten 'alive' nog even niet op true—dat gebeurt in spawn().)
     *
     * @param name        de weergavenaam van het monster (bv. “Backlog Beast”)
     * @param description korte tekstuele beschrijving (bv. “Hij bewaakt de Product Backlog…”)
     * @param publisher   de EventPublisher uit GameContext (bijv. context.getPublisher())
     * @param context     de GameContext zelf (in geval je daar informatie uit haalt)
     */
    public Monster(String name, String description, EventPublisher<?> publisher, GameContext context) {
        this.name        = name;
        this.description = description;
        this.publisher   = publisher;
        this.context     = context;
        this.alive       = false;    // staat nu nog “dood”—spawn() moet dit op true zetten
    }

    // ================================
    //    TEMPLATEMETHODE: engage()
    // ================================
    /**
     * Dit is de vaste volgorde waarin een monster “acties” uitvoert:
     *
     *  1) spawn()       → initialiseer en zet alive = true
     *  2) zolang levend: takeTurn() → voer per beurt/ronde de eigen logica uit
     *  3) wanneer niet levend: die() → cleanup / melding / lootpaden / UI‐feedback
     *
     * Iedere concrete subklasse zegt zelf wat hij doet in spawn(), takeTurn() en die().
     */
    public final void engage() {
        // Stap 1: spawn‐fase (bv. monster verschijnt, zet alive = true)
        spawn();

        // Stap 2: herhaal “per beurt” zolang alive == true
        while (alive) {
            takeTurn();
        }

        // Stap 3: op het moment dat alive == false (monster is verslagen / stopt),
        // roep je de afhandelmethode aan:
        die();
    }

    // ================================
    //    ABSTRACTE HOOKS (door subklassen invullen)
    // ================================

    /**
     * Hook #1: Initialiseer hier alles wat nodig is op het moment dat het monster “verschijnt”.
     * Bijv.:
     *   - alive = true
     *   - een “MonsterAppears”‐melding publiceren (UI)
     *   - z’n start‐stats instellen (hp, buffs, etc.)
     */
    protected abstract void spawn();

    /**
     * Hook #2: Wordt in elke cyclus/ronde (turn) aangeroepen zolang alive == true.
     * Bijvoorbeeld:
     *   - Stel een Scrum‐vraag
     *   - Verlaag een timer, speel animaties, laat het monster iets op je af schieten
     *   - Controleer of de speler de vraag goed heeft beantwoord
     *   - Bij “fout” -> bereken zelf hoe het monster reageert (bijv. zelf health verliezen of de speler straf geven)
     *   - Mocht het monster hierdoor “dood” gaan: zet zelf alive = false
     *
     * Kortom: alle logica per dag/ronde/beurt komt hier.
     */
    protected abstract void takeTurn();

    /**
     * Hook #3: Zodra alive == false, roep je hier alles wat er moet gebeuren wanneer
     * het monster écht is verslagen of uitgeschakeld.
     * Bijv.:
     *   - Een “MonsterDefeated”‐melding publiceren
     *   - Loot give‐away of score verhogen
     *   - Eventueel verwijderen uit GameContext / de arena opruimen
     */
    protected abstract void die();

    // ================================
    //      OPTIONELE HELPERMETHODES
    // ================================

    /**
     * @return true als het monster op dit moment nog leeft.
     * Subklassen hoeven dit niet te overschrijven.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * @return de naam van het monster.
     */
    public String getName() {
        return name;
    }

    /**
     * @return de (korte) beschrijving van het monster.
     */
    public String getDescription() {
        return description;
    }
}
