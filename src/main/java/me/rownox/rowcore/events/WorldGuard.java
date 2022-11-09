package me.rownox.rowcore.events;

import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.utils.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class WorldGuard implements Listener {

    final FileConfiguration CONFIG = RowCore.getInstance().config;

    @EventHandler
    private void onBlockStateChange(BlockFormEvent e) {
        ConfigUtils.WorldGuardCancel(e, "block-state-change");
    }
}                                                               
