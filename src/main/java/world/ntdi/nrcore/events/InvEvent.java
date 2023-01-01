package world.ntdi.nrcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import world.ntdi.nrcore.NRCore;

public class InvEvent implements Listener {

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (NRCore.invsee.contains(p.getUniqueId()) && !p.isOp()) {
            e.setCancelled(true);
        }


    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        NRCore.invsee.remove(p.getUniqueId());
    }

}
