package world.ntdi.nrcore.utils.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Cancellable;
import world.ntdi.nrcore.NRCore;

import java.util.List;

public class ConfigUtils {

    private FileConfiguration CONFIG;

    public Location spawn;

    public String noPermission;

    public List<String> help;

    public String SENT_MESSAGE;
    public String FROM_MESSAGE;

    public Boolean SQLENABLED;
    public String SQLURL;
    public String SQLUSER;
    public String SQLPASSWORD;

    public static String PERMISSION_PREFIX;
    public void setSpawn(Location loc) {
        CONFIG.set("spawn.world", loc.getWorld().getName());
        CONFIG.set("spawn.x", loc.getBlockX());
        CONFIG.set("spawn.y", loc.getBlockY());
        CONFIG.set("spawn.z", loc.getBlockZ());
        CONFIG.set("spawn.yaw", loc.getYaw());
        CONFIG.set("spawn.pitch", loc.getPitch());
    }

    public void cancelWG(Cancellable e, final String path) {
        if (CONFIG.getBoolean("world-guard." + path)) {
            e.setCancelled(true);
        }
    }

    public ConfigUtils() {
        CONFIG = NRCore.getInstance().getConfig();

        spawn = new Location(Bukkit.getWorld(CONFIG.getString("spawn.world")),
                CONFIG.getDouble("spawn.x"),
                CONFIG.getDouble("spawn.y"),
                CONFIG.getDouble("spawn.z"),
                (float) CONFIG.getDouble("spawn.yaw"),
                (float) CONFIG.getDouble("spawn.pitch")
        );

        noPermission = CONFIG.getString("no-permission");
        help = CONFIG.getStringList("help");
        SENT_MESSAGE = CONFIG.getString("messaging.sent");
        FROM_MESSAGE = CONFIG.getString("messaging.from");
        SQLENABLED = CONFIG.getBoolean("sql.sql-enabled");
        SQLURL = CONFIG.getString("sql.sql-url");
        SQLUSER = CONFIG.getString("sql.sql-user");
        SQLPASSWORD = CONFIG.getString("sql.sql-password");
        PERMISSION_PREFIX = CONFIG.getString("permission-prefix");

        System.out.println(SQLENABLED);
    }
}
