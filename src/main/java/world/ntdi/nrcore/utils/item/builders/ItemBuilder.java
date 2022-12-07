package world.ntdi.nrcore.utils.item.builders;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    protected ItemStack item;
    protected int amount;
    protected int modelData;
    protected String name;
    protected List<String> lore;
    protected Map<Enchantment, Integer> enchantments;
    protected List<ItemFlag> flags;
    protected boolean glow;
    protected Material material;
    protected boolean unbreakable;

    public ItemBuilder() {
        this.material = Material.BEDROCK;
        this.lore = new ArrayList<>();
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
    }

    public ItemBuilder item(@NonNull final ItemStack item) {
        this.item = item;
        this.material = item.getType();
        return this;
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder amount(final int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder modelData(final int modelData) {
        this.modelData = modelData;
        return this;
    }

    public ItemBuilder name(@NonNull final String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder lores(@NonNull final List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder lores(@NonNull final String... lore) {
        this.lore = List.of(lore);
        return this;
    }

    public ItemBuilder lore(@NonNull final String... lore) {
        this.lore.addAll(List.of(lore));
        return this;
    }

    public ItemBuilder enchantments(@NonNull final Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemBuilder enchantment(@NonNull final Enchantment enchantment, final int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder flags(List<ItemFlag> flags) {
        this.flags = flags;
        return this;
    }

    public ItemBuilder flag(ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public ItemBuilder glow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemStack build() {

        if (this.item == null) {
            this.item = new ItemStack(this.material);
        }
        final ItemMeta meta = item.getItemMeta();

        if (this.amount != 0) {
            item.setAmount(this.amount);
        }

        if (this.name != null) {
            meta.setDisplayName(this.name);
        }

        if (this.lore != null) {
            meta.setLore(this.lore);
        }

        if (this.enchantments != null) {
            this.enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        }

        if (this.flags != null) {
            meta.addItemFlags(this.flags.toArray(new ItemFlag[0]));
        }

        if (this.glow) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
        }

        if (this.unbreakable) {
            meta.setUnbreakable(true);
        }

        if (this.modelData != 0) {
            meta.setCustomModelData(this.modelData);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static ItemBuilder of(@NonNull final Material material) {
        return new ItemBuilder().material(material);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount) {
        return new ItemBuilder().material(material).amount(amount);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount, final int modelData) {
        return new ItemBuilder().material(material).amount(amount).modelData(modelData);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount, @NonNull final String name) {
        return new ItemBuilder().material(material).amount(amount).name(name);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount, @NonNull final String name,
                                 @NonNull final String... lore) {
        return new ItemBuilder().material(material).amount(amount).name(name).lore(lore);
    }
}
