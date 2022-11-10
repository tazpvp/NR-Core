package me.rownox.nrcore.utils.item.builders;

import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherBuilder extends ItemBuilder {

    protected Color color;

    private LeatherBuilder(@NonNull final LeatherType type) {
        super();
        this.material = type.getMaterial();
    }

    public LeatherBuilder setColor(@NonNull final Color color) {
        this.color = color;
        return this;
    }

    @Override
    public ItemStack build() {
        final ItemStack stack = super.build();
        final LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();

        if (color != null) {
            meta.setColor(color);
        }

        stack.setItemMeta(meta);
        return stack;
    }

    public static LeatherBuilder of(@NonNull final LeatherType type) {
        return new LeatherBuilder(type);
    }

    public static LeatherBuilder of(@NonNull final LeatherType leatherType, final int amount) {
        return (LeatherBuilder) new LeatherBuilder(leatherType).amount(amount);
    }

    public static LeatherBuilder of(@NonNull final LeatherType leatherType, final int amount, final int modelData) {
        return (LeatherBuilder) new LeatherBuilder(leatherType).amount(amount).modelData(modelData);
    }

    public static LeatherBuilder of(@NonNull final LeatherType leatherType, final int amount,
                                    @NonNull final String name) {
        return (LeatherBuilder) new LeatherBuilder(leatherType).amount(amount).name(name);
    }

    public static LeatherBuilder of(@NonNull final LeatherType leatherType, final int amount,
                                    @NonNull final String name,
                                    @NonNull final String... lore) {
        return (LeatherBuilder) new LeatherBuilder(leatherType).amount(amount).name(name).lore(lore);
    }

    public enum LeatherType {
        HELMET(Material.LEATHER_HELMET),
        CHESTPLATE(Material.LEATHER_CHESTPLATE),
        LEGGINGS(Material.LEATHER_LEGGINGS),
        BOOTS(Material.LEATHER_BOOTS);

        private Material material;

        LeatherType(Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return material;
        }
    }
}
