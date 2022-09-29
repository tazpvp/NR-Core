package me.rownox.rowcore;

import com.google.gson.Gson;
import me.rownox.rowcore.commands.FlyCmd;
import me.rownox.rowcore.commands.GmCmd;
import me.rownox.rowcore.commands.HealCmd;
import me.rownox.rowcore.commands.home.HomeCmd;
import me.rownox.rowcore.commands.home.SethomeCmd;
import me.rownox.rowcore.data.HomesContainer;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

public final class RowCore extends JavaPlugin {
    public static RowCore instance;
    public FileConfiguration config = this.getConfig();
    public HomesContainer homesList;

    @Override
    public void onEnable() {

        instance = this;
        registerCommands();

        config.addDefault("No-Permission", " &cYou are missing the permission: ");
        config.options().copyDefaults(true);
        saveConfig();

        try {
            if (!getDataFolder().exists()) getDataFolder().mkdirs();

            File file = new File(getDataFolder(), "homes.json");

            if (!file.exists()) file.createNewFile();

            Gson gson = new Gson();

            if (file.length() == 0) {
                homesList = new HomesContainer();
            } else {
                Reader reader = new FileReader(file);
                homesList = gson.fromJson(reader, HomesContainer.class);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {

        try {
            File file = new File(getDataFolder(), "homes.json");

            Writer writer = new FileWriter(file, false);

            Gson gson = new Gson();
            gson.toJson(homesList, writer);
            writer.flush();
            writer.close();

            getLogger().info("Saved Data!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerCommands() { //makes sure the plugin knows about the command classes
        Map<String, CommandExecutor> commands = new HashMap<>() {{
            put("heal", new HealCmd());
            put("fly", new FlyCmd());
            put("gmc", new GmCmd(GameMode.CREATIVE));
            put("gms", new GmCmd(GameMode.SURVIVAL));
            put("gma", new GmCmd(GameMode.ADVENTURE));
            put("gmsp", new GmCmd(GameMode.SPECTATOR));
            put("home", new HomeCmd());
            put("sethome", new SethomeCmd());
        }};

        for (Map.Entry<String, CommandExecutor> command : commands.entrySet()) {
            getCommand(command.getKey()).setExecutor(command.getValue());
        }
    }

    public static RowCore getInstance(){
        return instance;
    }
}
