package world.ntdi.nrcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import world.ntdi.nrcore.NRCore;

public class WorldGuard implements Listener {

    @EventHandler
    public void onBlockStateChange(BlockPhysicsEvent e) {
        NRCore.config.cancelWG(e, "block-state-change");
    }
    @EventHandler
    public void onBlockStateChange(BlockFormEvent e) {
        NRCore.config.cancelWG(e, "block-state-change");
    }

    @EventHandler
    public void onBlockStateChange(BlockIgniteEvent e) {
        NRCore.config.cancelWG(e, "block-state-change");
    }

    @EventHandler
    public void onBlockStateChange(BlockFromToEvent e) {
        NRCore.config.cancelWG(e, "block-state-change");
    }

}                                                               
