package me.rownox.rowcore;

import me.rownox.rowcore.commands.cmdHeal;
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

        saveDefaultConfig();
    }

    public void registerCommands() { //makes sure the plugin knows about the command classes
        getCommand("heal").setExecutor(new cmdHeal());
    }

    public static RowCore getInstance(){
        return instance;
    }
}
