package world.ntdi.nrcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.nrcore.commands.*;
import world.ntdi.nrcore.commands.enderchest.EnderchestCommand;
import world.ntdi.nrcore.commands.fly.FlyCommand;
import world.ntdi.nrcore.commands.gamemode.GameModeCommand;
import world.ntdi.nrcore.commands.heal.HealCommand;
import world.ntdi.nrcore.commands.invsee.InvseeCommand;
import world.ntdi.nrcore.events.InvEvent;
import world.ntdi.nrcore.events.MoveEvent;
import world.ntdi.nrcore.events.WorldGuard;
import world.ntdi.nrcore.utils.command.CommandCL;
import world.ntdi.nrcore.utils.config.ConfigUtils;
import world.ntdi.nrcore.utils.sql.Database;
import world.ntdi.nrcore.utils.sql.DatabaseThread;

import java.util.ArrayList;
import java.util.Arrays;
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
        CommandCL.register(new HealCommand());
        CommandCL.register(new FlyCommand());
        CommandCL.register(new EnderchestCommand());
        CommandCL.register(new InvseeCommand());
        new MessageCommandFunction();
        new SpeedCommandFunction();
        new SkullCommandFunction();
        Arrays.stream(GameModeCommand.values()).forEach(command -> CommandCL.register(command.command()));
        new WorldGuardCommandFunction("worldguard", "worldguard", "wg");
    }

    public static NRCore getInstance(){
        return (NRCore) Bukkit.getPluginManager().getPlugin("NRCore");
    }
}
