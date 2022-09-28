package me.rownox.rowcore;

import me.rownox.rowcore.commands.FlyCmd;
import me.rownox.rowcore.commands.GmCmd;
import me.rownox.rowcore.commands.HealCmd;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
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
    }

    public void registerCommands() { //makes sure the plugin knows about the command classes
        Map<String, CommandExecutor> commands = new HashMap<>() {{
            put("heal", new HealCmd());
            put("fly", new FlyCmd());
            put("gmc", new GmCmd(GameMode.CREATIVE));
            put("gms", new GmCmd(GameMode.SURVIVAL));
            put("gma", new GmCmd(GameMode.ADVENTURE));
            put("gmsp", new GmCmd(GameMode.SPECTATOR));
        }};

        for (Map.Entry<String, CommandExecutor> command : commands.entrySet()) {
            getCommand(command.getKey()).setExecutor(command.getValue());
        }
    }

    public static RowCore getInstance(){
        return instance;
    }
}
