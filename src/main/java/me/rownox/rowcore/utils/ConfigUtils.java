package me.rownox.rowcore.utils;

import me.rownox.rowcore.RowCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    private static final FileConfiguration CONFIG = RowCore.getInstance().config;

    public static Location spawn;

    public static void setSpawn(Location loc) {
        CONFIG.set("spawn.world", loc.getWorld().getName());
        CONFIG.set("spawn.x", loc.getBlockX());
        CONFIG.set("spawn.y", loc.getBlockY());
        CONFIG.set("spawn.z", loc.getBlockZ());
        CONFIG.set("spawn.yaw", loc.getYaw());
        CONFIG.set("spawn.pitch", loc.getPitch());
    }


    static {
        spawn = new Location(Bukkit.getWorld(CONFIG.getString("spawn.world")),
                CONFIG.getInt("spawn.x"),
                CONFIG.getInt("spawn.y"),
                CONFIG.getInt("spawn.z"),
                (float) CONFIG.getDouble("spawn.yaw"),
                (float) CONFIG.getDouble("spawn.pitch")
        );
    }


}
