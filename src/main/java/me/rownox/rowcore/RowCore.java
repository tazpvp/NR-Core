package me.rownox.rowcore;

import me.rownox.rowcore.commands.*;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

public final class RowCore extends JavaPlugin {
    public static RowCore instance;
    public FileConfiguration config = this.getConfig();

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
        new HelpCommandExecutor();
        new SpawnCommandExecutor("set");
        new SpawnCommandExecutor("teleport");
        new HealCommandExecutor();
        new FlyCommandExecutor();
        new GamemodeCommandExecutor(GameMode.CREATIVE, "gmc");
        new GamemodeCommandExecutor(GameMode.SURVIVAL, "gms");
        new GamemodeCommandExecutor(GameMode.ADVENTURE, "gma");
        new GamemodeCommandExecutor(GameMode.SPECTATOR, "gmsp");
    }

    public static RowCore getInstance(){
        return instance;
    }
}
