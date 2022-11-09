package me.rownox.rowcore.utils.item;

import me.rownox.rowcore.utils.collections.RegistryTemplate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemRegistry extends RegistryTemplate<String, ItemStack> {

    private static ItemRegistry instance;

    private ItemRegistry() {
        super(new ItemStack(Material.AIR));
    }

    public static ItemRegistry getInstance() {
        if (instance == null) {
            instance = new ItemRegistry();
        }
        return instance;
    }

}
