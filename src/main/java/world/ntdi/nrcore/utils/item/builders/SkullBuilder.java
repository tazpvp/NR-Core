package world.ntdi.nrcore.utils.item.builders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullBuilder extends ItemBuilder {
    private ItemStack itemStack;
    private String texture;

    private SkullBuilder() {
        super();
        this.material = Material.PLAYER_HEAD;
    }

    public SkullBuilder setHeadTexture(String texture) {
        this.texture = texture;
        return this;
    }

    @Override
    public ItemStack build() {
        final ItemStack stack = super.build();
        final SkullMeta meta = (SkullMeta) stack.getItemMeta();

        if (texture != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", this.texture));
            Field field;
            try {
                field = meta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(meta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            stack.setItemMeta(meta);
        }

        return stack;
    }

    public static SkullBuilder of() {
        return new SkullBuilder();
    }

    public static SkullBuilder of(final int amount) {
        return (SkullBuilder) new SkullBuilder().amount(amount);
    }

    public static SkullBuilder of(final int amount, final int modelData) {
        return (SkullBuilder) new SkullBuilder().amount(amount).modelData(modelData);
    }

    public static SkullBuilder of(final int amount, @NonNull final String name) {
        return (SkullBuilder) new SkullBuilder().amount(amount).name(name);
    }

    public static SkullBuilder of(final int amount, @NonNull final String name,
                                   @NonNull final String... lore) {
        return (SkullBuilder) new SkullBuilder().amount(amount).name(name).lore(lore);
    }
}
