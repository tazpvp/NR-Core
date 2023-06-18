package world.ntdi.nrcore.utils.holograms;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.ChatUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Hologram {
    public static NamespacedKey key = new NamespacedKey(NRCore.getInstance(), "Hologram");

    @Getter
    private UUID id;
    @Getter
    private String[] text;
    @Getter
    private Location spawnLoc;
    @Getter
    private boolean baby;

    private List<ArmorStand> armorStands;

    public Hologram(String[] text, Location spawnLoc, boolean baby) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.spawnLoc = spawnLoc;
        this.baby = baby;
        this.armorStands = new LinkedList<>();

        initializeHolograms();
    }

    public Hologram(String text, Location spawnLoc, boolean baby) {
        new Hologram(new String[]{text}, spawnLoc, baby);
    }

    public Hologram(Location spawnLoc, boolean baby, String... text) {
        new Hologram(text, spawnLoc, baby);
    }

    private void initializeHolograms() {
        Location spawnLocClone = spawnLoc.clone();
        for (String s : text) {
            createHologram(s, spawnLocClone.subtract(0, 0.3, 0));
        }
    }

    private void createHologram(String name, Location location) {
        ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        as.setGravity(false);
        as.setInvisible(true);
        as.setInvulnerable(true);
        as.setSmall(baby);

        as.setCustomNameVisible(true);
        as.setCustomName(ChatUtils.chat(name));

        as.getPersistentDataContainer().set(Hologram.key, PersistentDataType.STRING, getId().toString());

        armorStands.add(as);
    }

    /**
     * Update the hologram's display text
     * @param name The new text for each line
     */
    public void updateHologram(String... name) {
        if (getText().length != name.length) {
            throw new ArrayIndexOutOfBoundsException("Cannot add lines to already defined length hologram");
        }
        for (int i = 0; i < armorStands.size(); i++) {
            armorStands.get(i).setCustomName(name[i]);
        }
    }

    /**
     * Update the hologram's location
     * @param location The new location for the hologram
     */
    public void updateHologram(Location location) {
        this.spawnLoc = location;
        for (int i = 0; i < armorStands.size(); i++) {
            armorStands.get(i).remove();
            createHologram(text[i], location.subtract(0, 0.3, 0));
        }
    }

    /**
     * Delete the entire hologram
     */
    public void deleteHologram() {
        getSpawnLoc().getWorld().getEntities().stream().forEach(entity -> {
            if (entity instanceof ArmorStand as) {
                if (as.getPersistentDataContainer().get(Hologram.key, PersistentDataType.STRING).equals(getId().toString())) {
                    entity.remove();
                }
            }
        });
    }

    /**
     * Adds this hologram to a deletion coroutine when the server closes.
     */
    public void deleteOnServerClose() {
        NRCore.toBeDeleted.add(this);
    }
}
