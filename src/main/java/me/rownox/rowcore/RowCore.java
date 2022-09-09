package me.rownox.rowcore;

import me.rownox.rowcore.commands.FlyCMD;
import me.rownox.rowcore.commands.HealCMD;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, CommandExecutor> commands = new HashMap<>() {{
            put("heal", new HealCMD());
            put("fly", new FlyCMD());
        }};

        for (Map.Entry<String, CommandExecutor> command : commands.entrySet()) {
            getCommand(command.getKey()).setExecutor(command.getValue());
        }
    }

    public static RowCore getInstance(){
        return instance;
    }
}
