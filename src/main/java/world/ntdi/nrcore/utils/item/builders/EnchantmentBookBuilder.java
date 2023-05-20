package world.ntdi.nrcore.utils.item.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class EnchantmentBookBuilder extends ItemBuilder {
    protected Map<Enchantment, Integer> enchantmentIntegerMap;

    private EnchantmentBookBuilder() {
        super();
        this.material = Material.ENCHANTED_BOOK;
    }

    @Override
    public ItemStack build() {
        final ItemStack stack = super.build();
        stack.getEnchantments().keySet().forEach(stack::removeEnchantment);

        final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) stack.getItemMeta();

        for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : this.enchantmentIntegerMap.entrySet()) {

        }

        this.enchantmentIntegerMap.entrySet().forEach(enchantmentIntegerEntry -> {
            meta.addStoredEnchant(enchantmentIntegerEntry.getKey(), enchantmentIntegerEntry.getValue(), true);
        });

        stack.setItemMeta(meta);

        return super.build();
    }
}
