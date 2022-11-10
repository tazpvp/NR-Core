package me.rownox.nrcore.utils;

import me.rownox.nrcore.NRCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigUtils {

    private static final FileConfiguration CONFIG = NRCore.getInstance().config;

    public static Location spawn;

    public static String noPermission = CONFIG.getString("no-permission");

    public static List<String> help = CONFIG.getStringList("help");

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