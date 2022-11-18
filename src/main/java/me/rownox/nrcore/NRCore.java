package me.rownox.nrcore;

import lombok.Getter;
import me.rownox.nrcore.commands.*;
import me.rownox.nrcore.events.WorldGuard;
import me.rownox.nrcore.utils.ConfigUtils;
import me.rownox.nrcore.utils.sql.DatabaseThread;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

public final class NRCore extends JavaPlugin {
    public static NRCore instance;
    @Getter
    private static DatabaseThread databaseThread;
    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();

        config.addDefault("no-permission", " &cYou are missing the permission: ");
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new WorldGuard(), this);

        if (ConfigUtils.SQLENABLED) {
            databaseThread = new DatabaseThread();
            CompletableFuture.runAsync(databaseThread::start); //.thenRun(SQLHelper::initTable)
        }
    }

    @Override
    public void onDisable() {

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
