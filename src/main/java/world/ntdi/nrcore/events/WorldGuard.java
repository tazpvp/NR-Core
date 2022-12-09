package world.ntdi.nrcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import world.ntdi.nrcore.utils.ConfigUtils;

public class WorldGuard implements Listener {

    @EventHandler
    public void onBlockStateChange(BlockPhysicsEvent e) {
        ConfigUtils.cancelWG(e, "block-state-change");
    }
    @EventHandler
    public void onBlockStateChange(BlockFormEvent e) {
        ConfigUtils.cancelWG(e, "block-state-change");
    }

    @EventHandler
    public void onBlockStateChange(BlockIgniteEvent e) {
        ConfigUtils.cancelWG(e, "block-state-change");
    }

    @EventHandler
    public void onBlockStateChange(BlockFromToEvent e) {
        ConfigUtils.cancelWG(e, "block-state-change");
    }

}                                                               
