package world.ntdi.nrcore.utils.item.builders;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class EnchantmentBookBuilder {
    protected Material material;
    protected Map<Enchantment, Integer> enchantmentIntegerMap;

    public EnchantmentBookBuilder() {
        this.material = Material.ENCHANTED_BOOK;
    }

    public EnchantmentBookBuilder enchantment(@NonNull final Enchantment enchantment, final int level) {
        this.enchantmentIntegerMap.put(enchantment, level);
        return this;
    }

    public ItemStack build() {
        final ItemStack stack = new ItemStack(this.material);
        stack.getEnchantments().keySet().forEach(stack::removeEnchantment);

        final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) stack.getItemMeta();

        this.enchantmentIntegerMap.entrySet().forEach(enchantmentIntegerEntry -> {
            meta.addStoredEnchant(enchantmentIntegerEntry.getKey(), enchantmentIntegerEntry.getValue(), true);
        });

        stack.setItemMeta(meta);

        return stack;
    }
}
