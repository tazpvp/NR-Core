package world.ntdi.nrcore.utils.item.custom;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import world.ntdi.nrcore.NRCore;

public abstract class CustomItem implements Listener {
    @Getter
    private final String name;
    @Getter
    private final String[] description;
    @Getter
    private final Material material;

    public CustomItem(String name, String[] description, Material material) {
        this.name = name;
        this.description = description;
        this.material = material;
        Bukkit.getServer().getPluginManager().registerEvents(this, NRCore.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            onLeftClick(e);
            return;
        }
        onRightClick(e);
    }

    public abstract void onLeftClick(PlayerInteractEvent e);
    public abstract void onRightClick(PlayerInteractEvent e);

}
