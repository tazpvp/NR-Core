package world.ntdi.nrcore.utils.item.builders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
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

    public SkullBuilder setHeadTexture(OfflinePlayer p) {
        return setHeadTexture(p.getUniqueId());
    }

    public SkullBuilder setHeadTexture(UUID uuid)  {
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
            InputStreamReader read = new InputStreamReader(url.openStream());
            JsonObject textureProperty = new JsonParser().parse(read).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            return setHeadTexture(texture);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public ItemStack build() {
        final ItemStack stack = super.build();
        final SkullMeta meta = (SkullMeta) stack.getItemMeta();

        if (texture != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
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
