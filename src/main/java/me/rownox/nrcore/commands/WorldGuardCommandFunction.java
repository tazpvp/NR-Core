package me.rownox.nrcore.commands;

import lombok.NonNull;
import me.rownox.nrcore.NRCore;
import me.rownox.nrcore.utils.command.CommandCore;
import me.rownox.nrcore.utils.command.CommandFunction;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WorldGuardCommandFunction extends CommandCore implements CommandFunction {

    public WorldGuardCommandFunction(@NonNull String name, String permission, @NonNull String... alias) {
        super(name, permission, alias);
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        FileConfiguration CONFIG = NRCore.getInstance().config;

        if (args[0].equalsIgnoreCase("blockstatechange")) {
            if (Boolean.valueOf(CONFIG.getString("world-guard.block-state-change"))) {
                CONFIG.set("world-guard.block-state-change", "false");
            } else {
                CONFIG.set("world-guard.block-state-change", "true");
            }

            if (sender instanceof Player) {
                sender.sendMessage(
                    net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', CONFIG.getString("theme.secondary")) + "Block state changing was set to: " +
                    net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', CONFIG.getString("theme.main")) + CONFIG.getString("world-guard.block-state-change").toUpperCase()
                );
            }

        }
    }
}
