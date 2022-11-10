package me.rownox.nrcore.events;

import me.rownox.nrcore.NRCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class WorldGuard implements Listener {

    private final FileConfiguration CONFIG = NRCore.getInstance().config;

    @EventHandler
    public void onBlockStateChange(BlockFormEvent e) {
        WorldGuardCancel(e, "block-state-change");
    }


    @SuppressWarnings("all")
    private void WorldGuardCancel(Cancellable e, String path) {
        if (CONFIG.getBoolean("world-guard." + path)) {
            e.setCancelled(true);
        }
    }
}                                                               