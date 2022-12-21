package world.ntdi.nrcore.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import world.ntdi.nrcore.NRCore;

import java.util.UUID;
import java.util.WeakHashMap;

public class MoveEvent implements Listener {

    public static WeakHashMap<UUID, Location> loc = new WeakHashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (p.hasMetadata("goingToSpawn")) {
            if (!checkLoc(p.getUniqueId())) {
                p.sendMessage(ChatColor.DARK_AQUA + "Teleportation cancelled.");
                p.removeMetadata("goingToSpawn", NRCore.getInstance());
                loc.remove(p.getUniqueId());
            }
        }

    }

    public boolean checkLoc(UUID id) {
        Player p = Bukkit.getPlayer(id);
        Location l = p.getLocation();

        if (l.getX() != loc.get(id).getX() || l.getZ() != loc.get(id).getZ()) {
            return false;
        }
        return true;
    }
}
