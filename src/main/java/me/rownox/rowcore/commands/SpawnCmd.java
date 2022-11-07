package me.rownox.rowcore.commands;

import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.utils.ConfigUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;

public class SpawnCmd implements CommandExecutor {
    private final String spawnType;

    public SpawnCmd(String type) {
        this.spawnType = type;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {

            final Plugin PLUGIN = RowCore.getInstance();
            final FileConfiguration CONFIG = RowCore.getInstance().config;

            if (spawnType.equals("set")) {
                if (checkPerms(p,"spawn.set")) return false;

                Location loc = p.getLocation();
                ConfigUtils.setSpawn(loc);

                return true;

            } else if (spawnType.equals("teleport")) {

                p.sendMessage(ChatColor.DARK_AQUA + "You'll be teleported to spawn in " + ChatColor.AQUA + CONFIG.getString("teleport.delay") + " Seconds" + ChatColor.DARK_AQUA + " Do not move.");
                p.setMetadata("goingToSpawn", new FixedMetadataValue(PLUGIN, true));
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if (p.hasMetadata("goingToSpawn")){
                            p.removeMetadata("goingToSpawn", PLUGIN);

                            p.teleport(ConfigUtils.spawn);
                            p.sendMessage(ChatColor.DARK_AQUA + "Teleportation complete.");

                            p.playSound(p.getLocation(), Sound.valueOf(CONFIG.getString("teleport.sound")), 1, 1);
                        }
                    }
                }.runTaskLater(PLUGIN, 5 * 20);
            }
        }
        return false;
    }
}
