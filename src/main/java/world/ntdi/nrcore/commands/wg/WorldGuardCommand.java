package world.ntdi.nrcore.commands.wg;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class WorldGuardCommand extends NRCommand {
    public WorldGuardCommand() {
        super(new Label("wg", "nrcore.wg"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }
        FileConfiguration CONFIG = NRCore.getInstance().getConfig();

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
        return true;
    }
}
