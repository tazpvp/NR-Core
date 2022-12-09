package world.ntdi.nrcore.utils;

import org.bukkit.event.Cancellable;
import world.ntdi.nrcore.NRCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public final class ConfigUtils {

    private static final FileConfiguration CONFIG = NRCore.getInstance().config;

    public static Location spawn;

    public static String noPermission = CONFIG.getString("no-permission");

    public static List<String> help = CONFIG.getStringList("help");

    public static Boolean SQLENABLED = CONFIG.getBoolean("sql.sql-enabled");
    public static String SQLURL = CONFIG.getString("sql.sql-url");
    public static String SQLUSER = CONFIG.getString("sql.sql-user");
    public static String SQLPASSWORD = CONFIG.getString("sql.sql-password");

    public static void setSpawn(Location loc) {
        CONFIG.set("spawn.world", loc.getWorld().getName());
        CONFIG.set("spawn.x", loc.getBlockX());
        CONFIG.set("spawn.y", loc.getBlockY());
        CONFIG.set("spawn.z", loc.getBlockZ());
        CONFIG.set("spawn.yaw", loc.getYaw());
        CONFIG.set("spawn.pitch", loc.getPitch());
    }

    public static void cancelWG(Cancellable e, final String path) {
        if (CONFIG.getBoolean("world-guard." + path)) {
            e.setCancelled(true);
        }
    }


    static {
        spawn = new Location(Bukkit.getWorld(CONFIG.getString("spawn.world")),
                CONFIG.getDouble("spawn.x"),
                CONFIG.getDouble("spawn.y"),
                CONFIG.getDouble("spawn.z"),
                (float) CONFIG.getDouble("spawn.yaw"),
                (float) CONFIG.getDouble("spawn.pitch")
        );
    }
}
