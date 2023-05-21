package world.ntdi.nrcore;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.nrcore.commands.*;
import world.ntdi.nrcore.events.InvEvent;
import world.ntdi.nrcore.events.MoveEvent;
import world.ntdi.nrcore.events.WorldGuard;
import world.ntdi.nrcore.utils.config.ConfigUtils;
import world.ntdi.nrcore.utils.sql.Database;
import world.ntdi.nrcore.utils.sql.DatabaseThread;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class NRCore extends JavaPlugin {

    private static DatabaseThread databaseThread;
    public static ConfigUtils config;
    public static List<UUID> invsee = new ArrayList<>();

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        config = new ConfigUtils();

        registerCommands();

        getServer().getPluginManager().registerEvents(new WorldGuard(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new InvEvent(), this);

        resetDatabaseConnection();
    }

    @Override
    public void onDisable() {
        if (config.SQLENABLED) {
            databaseThread.getDB().closeDefault();
        }
    }

    public static void resetDatabaseConnection() {
        if (config.SQLENABLED) {
            databaseThread = new DatabaseThread(new Database());
            CompletableFuture.runAsync(databaseThread::start);
        }
    }


    public static DatabaseThread getDatabaseThread() {
        return databaseThread;
    }

    public void registerCommands() { //makes sure the plugin knows about the command classes
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
