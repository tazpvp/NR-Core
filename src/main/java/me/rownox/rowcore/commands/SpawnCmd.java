package me.rownox.rowcore.commands;

import me.rownox.rowcore.RowCore;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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

            final FileConfiguration CONFIG = RowCore.getInstance().config;

            if (spawnType.equals("set")) {
                if (checkPerms(p,"spawn.set")) return false;

                Location loc = p.getLocation();

                CONFIG.set("spawn.world", loc.getWorld().getName());
                CONFIG.set("spawn.x", loc.getBlockX());
                CONFIG.set("spawn.y", loc.getBlockY());
                CONFIG.set("spawn.z", loc.getBlockZ());
                CONFIG.set("spawn.yaw", loc.getYaw());
                CONFIG.set("spawn.pitch", loc.getPitch());

                return true;

            } else if (spawnType.equals("teleport")) {

                new BukkitRunnable() {
                    public void run() {

                    }
                }.runTaskTimer(RowCore.getInstance(), 20*5, 20);
            }
        }
        return false;
    }
}