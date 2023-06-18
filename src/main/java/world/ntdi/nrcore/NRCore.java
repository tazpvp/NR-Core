package world.ntdi.nrcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.nrcore.commands.enderchest.EnderchestCommand;
import world.ntdi.nrcore.commands.fly.FlyCommand;
import world.ntdi.nrcore.commands.gamemode.GameModeCommand;
import world.ntdi.nrcore.commands.heal.HealCommand;
import world.ntdi.nrcore.commands.invsee.InvseeCommand;
import world.ntdi.nrcore.commands.message.MessageCommand;
import world.ntdi.nrcore.commands.message.ReplyCommand;
import world.ntdi.nrcore.commands.skull.SkullCommand;
import world.ntdi.nrcore.commands.speed.SpeedCommand;
import world.ntdi.nrcore.commands.wg.WorldGuardCommand;
import world.ntdi.nrcore.events.InvEvent;
import world.ntdi.nrcore.events.MoveEvent;
import world.ntdi.nrcore.events.WorldGuard;
import world.ntdi.nrcore.utils.command.CommandCL;
import world.ntdi.nrcore.utils.config.ConfigUtils;
import world.ntdi.nrcore.utils.holograms.Hologram;
import world.ntdi.nrcore.utils.profanity.ProfanityFilter;
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
    public static List<Hologram> toBeDeleted = new ArrayList<>();

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

        ProfanityFilter.loadConfigs();
    }

    @Override
    public void onDisable() {
        if (config.SQLENABLED) {
            databaseThread.getDB().closeDefault();
        }
        if (!toBeDeleted.isEmpty()) {
            toBeDeleted.forEach(Hologram::deleteHologram);
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
        CommandCL.register(new HealCommand());
        CommandCL.register(new FlyCommand());
        CommandCL.register(new EnderchestCommand());
        CommandCL.register(new InvseeCommand());
        CommandCL.register(new SpeedCommand());
        CommandCL.register(new MessageCommand());
        CommandCL.register(new ReplyCommand());
        CommandCL.register(new SkullCommand());
        Arrays.stream(GameModeCommand.values()).forEach(command -> CommandCL.register(command.command()));
        CommandCL.register(new WorldGuardCommand());
    }

    public static NRCore getInstance(){
        return (NRCore) Bukkit.getPluginManager().getPlugin("NRCore");
    }
}
