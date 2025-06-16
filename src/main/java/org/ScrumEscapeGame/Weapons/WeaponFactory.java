package org.ScrumEscapeGame.Weapons;

import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Items.ItemFactory;
import org.ScrumEscapeGame.Items.Rarity;

import java.util.*;
import java.util.function.Supplier;

public class WeaponFactory implements ItemFactory {
    private final Random random = new Random();
    private final Map<Rarity, List<Supplier<Weapon>>> weaponOptions;

    public WeaponFactory() {
        weaponOptions = new HashMap<>();

        // Define common weapons: StraightSword is the only candidate in the common bucket.
        weaponOptions.put(Rarity.COMMON, Arrays.asList(
                () -> new StraightSword(generateId(),
                        "A sturdy straight sword",
                        3,
                        3)
        ));

        // Define uncommon weapons: Katana in this case.
        weaponOptions.put(Rarity.UNCOMMON, Arrays.asList(
                () -> new Katana(generateId(),
                        "A sharp katana",
                        1,
                        5)
        ));

        // Define rare weapons: Excalibur here.
        weaponOptions.put(Rarity.RARE, Arrays.asList(
                () -> new Excalibur(generateId(),
                        "The legendary Excalibur",
                        5,
                        10)
        ));
    }

    @Override
    public Item createItem(Room room, Rarity rarity) {
        List<Supplier<Weapon>> candidates = weaponOptions.get(rarity);
        if (candidates != null && !candidates.isEmpty()) {
            Supplier<Weapon> selectedSupplier = candidates.get(random.nextInt(candidates.size()));
            return (Item) selectedSupplier.get();
        }
        // Fallback to common if something goes awry.
        return new StraightSword(generateId(), "A sturdy straight sword", 2, 1);
    }

    private int generateId() {
        // This is a simple ID generator. You might want a more robust system.
        return random.nextInt(1000) + 1000;
    }
}

