package me.rownox.rowcore;

import me.rownox.rowcore.commands.*;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

public final class RowCore extends JavaPlugin {
    public static RowCore instance;
    public FileConfiguration config = this.getConfig();

    public Map<String, CommandExecutor> commands = new HashMap<>() {{
        put("heal", new HealCommandExecutor());
        put("fly", new FlyCommandExecutor());
        put("gmc", new GamemodeCommandExecutor(GameMode.CREATIVE));
        put("gms", new GamemodeCommandExecutor(GameMode.SURVIVAL));
        put("gma", new GamemodeCommandExecutor(GameMode.ADVENTURE));
        put("gmsp", new GamemodeCommandExecutor(GameMode.SPECTATOR));
        put("debug", new DebugCommandExecutor());
        put("setspawn", new SpawnCommandExecutor("set"));
        put("spawn", new SpawnCommandExecutor("teleport"));
        put("help", new HelpCommandExecutor());
    }};

    @Override
    public void onEnable() {

        instance = this;
        registerCommands();

        config.addDefault("No-Permission", " &cYou are missing the permission: ");
        config.options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public void onDisable() {

    }

    public void registerCommands() { //makes sure the plugin knows about the command classes

        for (Map.Entry<String, CommandExecutor> command : commands.entrySet()) {
            getCommand(command.getKey()).setExecutor(command.getValue());
        }
    }

    public static RowCore getInstance(){
        return instance;
    }
}
