package world.ntdi.nrcore;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.nrcore.commands.*;
import world.ntdi.nrcore.events.WorldGuard;
import world.ntdi.nrcore.utils.ConfigUtils;
import world.ntdi.nrcore.utils.sql.DatabaseThread;
import world.ntdi.nrcore.utils.sql.SQLHelper;

import java.util.concurrent.CompletableFuture;

public final class NRCore extends JavaPlugin {
    public static NRCore instance;

    private static DatabaseThread databaseThread;
    public FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        config = this.getConfig();
        registerCommands();

        config.options().copyDefaults(true);
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new WorldGuard(), this);

        resetDatabaseConnection();
        Bukkit.getLogger().info(String.valueOf(SQLHelper.ifRowExists("stats", "id", "huaman")));
    }

    @Override
    public void onDisable() {
        databaseThread.getDB().closeDefault();
    }

    public static void resetDatabaseConnection() {
        if (ConfigUtils.SQLENABLED) {
            databaseThread = new DatabaseThread();
            CompletableFuture.runAsync(databaseThread::start);
        }
    }


    public static DatabaseThread getDatabaseThread() {
        return databaseThread;
    }

    public void registerCommands() { //makes sure the plugin knows about the command classes
        new HelpCommandFunction();
        new SpawnCommandFunction("set");
        new SpawnCommandFunction("");
        new HealCommandFunction();
        new FlyCommandFunction();
        new GamemodeCommandFunction(GameMode.CREATIVE, "gmc");
        new GamemodeCommandFunction(GameMode.SURVIVAL, "gms");
        new GamemodeCommandFunction(GameMode.ADVENTURE, "gma");
        new GamemodeCommandFunction(GameMode.SPECTATOR, "gmsp");
        new WorldGuardCommandFunction("worldguard", "worldguard", "wg");
    }

    public static NRCore getInstance(){
        return instance;
    }
}
