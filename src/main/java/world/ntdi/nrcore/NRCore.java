package world.ntdi.nrcore;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.nrcore.commands.*;
import world.ntdi.nrcore.events.WorldGuard;
import world.ntdi.nrcore.utils.config.ConfigUtils;
import world.ntdi.nrcore.utils.sql.Database;
import world.ntdi.nrcore.utils.sql.DatabaseThread;

import java.util.concurrent.CompletableFuture;

public final class NRCore extends JavaPlugin {
    private static DatabaseThread databaseThread;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        config = this.getConfig();
        registerCommands();

        config.options().copyDefaults(true);
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new WorldGuard(), this);

        resetDatabaseConnection();
    }

    @Override
    public void onDisable() {
        if (ConfigUtils.SQLENABLED) {
            databaseThread.getDB().closeDefault();
        }
    }

    public static void resetDatabaseConnection() {
        if (ConfigUtils.SQLENABLED) {
            databaseThread = new DatabaseThread(new Database());
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
        new ViewEnderchestCommandFunction();
        new MessageCommandFunction();
        new SpeedCommandFunction();
        new SkullCommandFunction();
        new GamemodeCommandFunction(GameMode.CREATIVE, "gmc");
        new GamemodeCommandFunction(GameMode.SURVIVAL, "gms");
        new GamemodeCommandFunction(GameMode.ADVENTURE, "gma");
        new GamemodeCommandFunction(GameMode.SPECTATOR, "gmsp");
        new WorldGuardCommandFunction("worldguard", "worldguard", "wg");
    }

    public static NRCore getInstance(){
        return (NRCore) Bukkit.getPluginManager().getPlugin("NRCore");
    }
}
