package me.rownox.rowcore;

import me.rownox.rowcore.commands.cmdHeal;
import org.bukkit.plugin.java.JavaPlugin;

public final class RowCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() { //makes sure the plugin knows about the command classes
        this.getCommand("heal").setExecutor(new cmdHeal());
    }
}
