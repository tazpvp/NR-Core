package me.rownox.rowcore;

import me.rownox.rowcore.commands.*;
import me.rownox.rowcore.utils.item.builders.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
        new HelpCommandFunction();
        new SpawnCommandFunction("set");
        new SpawnCommandFunction("");
        new HealCommandFunction();
        new FlyCommandFunction();
        new GamemodeCommandFunction(GameMode.CREATIVE, "gmc");
        new GamemodeCommandFunction(GameMode.SURVIVAL, "gms");
        new GamemodeCommandFunction(GameMode.ADVENTURE, "gma");
        new GamemodeCommandFunction(GameMode.SPECTATOR, "gmsp");
    }

    public static RowCore getInstance(){
        return instance;
    }
}
