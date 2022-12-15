package world.ntdi.nrcore.utils.holograms;

import lombok.Getter;
import world.ntdi.nrcore.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.LinkedList;
import java.util.List;

public class Hologram {
    @Getter
    private String[] text;
    @Getter
    private Location spawnLoc;
    @Getter
    private boolean baby;

    private List<ArmorStand> armorStands;

    public Hologram(String[] text, Location spawnLoc, boolean baby) {
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
        armorStands.add(as);
    }

    /**
     * Update the hologram's display text
     * @param name The new text for each line
     */
    public void updateHologram(String... name) {
        if (text.length != name.length) {
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
        for (ArmorStand as : armorStands) {
            as.remove();
        }
    }
}
