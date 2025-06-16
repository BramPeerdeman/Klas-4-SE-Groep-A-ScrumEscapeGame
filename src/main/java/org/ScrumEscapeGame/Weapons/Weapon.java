package org.ScrumEscapeGame.Weapons;

public interface Weapon {
    int attack();
    void takeDurabilityDamage(int durabilityDamage);

    String getName();

    int getDurability();
}
