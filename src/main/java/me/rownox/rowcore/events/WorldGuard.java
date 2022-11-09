package me.rownox.rowcore.events;

import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.utils.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFormEvent;

public class WorldGuard implements Listener {

    private final FileConfiguration CONFIG = RowCore.getInstance().config;

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
