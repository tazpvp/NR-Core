package me.rownox.nrcore.utils.item.builders;

import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

public class PotionBuilder extends ItemBuilder {
    private PotionData potionData;
    private Color color;

    private PotionBuilder(PotionType type) {
        super();
        this.material = type.getMaterial();
    }

    public PotionBuilder setPotionData(@NonNull final PotionData potionData) {
        this.potionData = potionData;
        return this;
    }

    public PotionBuilder setColor(@NonNull final Color color) {
        this.color = color;
        return this;
    }

    public PotionBuilder setColor(final int r, final int g, final int b) {
        this.color = Color.fromRGB(r, g, b);
        return this;
    }

    @Override
    public ItemStack build() {
        final ItemStack stack = super.build();
        final PotionMeta meta = (PotionMeta) stack.getItemMeta();

        if (potionData != null) {
            meta.setBasePotionData(potionData);
        }

        if (color != null) {
            meta.setColor(color);
        }

        stack.setItemMeta(meta);
        return stack;
    }

    public static PotionBuilder of(@NonNull final PotionType type) {
        return new PotionBuilder(type);
    }

    public static PotionBuilder of(@NonNull final PotionType potionType, final int amount) {
        return (PotionBuilder) new PotionBuilder(potionType).amount(amount);
    }

    public static PotionBuilder of(@NonNull final PotionType potionType, final int amount, final int modelData) {
        return (PotionBuilder) new PotionBuilder(potionType).amount(amount).modelData(modelData);
    }

    public static PotionBuilder of(@NonNull final PotionType potionType, final int amount, @NonNull final String name) {
        return (PotionBuilder) new PotionBuilder(potionType).amount(amount).name(name);
    }

    public static PotionBuilder of(@NonNull final PotionType potionType, final int amount, @NonNull final String name,
                                   @NonNull final String... lore) {
        return (PotionBuilder) new PotionBuilder(potionType).amount(amount).name(name).lore(lore);
    }

    public enum PotionType {

        NORMAL(Material.POTION),
        SPLASH(Material.SPLASH_POTION),
        LINGERING(Material.LINGERING_POTION);

        private Material material;

        PotionType(@NonNull final Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return this.material;
        }
    }
}
