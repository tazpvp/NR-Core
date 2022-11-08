package me.rownox.rowcore.commands;

import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.utils.ConfigUtils;
import me.rownox.rowcore.utils.command.CommandCore;
import me.rownox.rowcore.utils.command.CommandFunction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;

public class SpawnCommandFunction extends CommandCore implements CommandFunction {
    private final String spawnType;

    public SpawnCommandFunction(String type) {
        super("spawn", null, "spawn");
        this.spawnType = type;
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {

            final RowCore ROWCORE = RowCore.getInstance();
            final FileConfiguration CONFIG = RowCore.getInstance().config;

            if (spawnType.equals("set")) {
                if (checkPerms(p,"spawn.set")) return;

                Location loc = p.getLocation();
                ConfigUtils.setSpawn(loc);

            } else if (spawnType.equals("teleport")) {

                p.sendMessage(ChatColor.DARK_AQUA + "You'll be teleported to spawn in " + ChatColor.AQUA + CONFIG.getString("teleport.delay") + " Seconds" + ChatColor.DARK_AQUA + " Do not move.");
                p.setMetadata("goingToSpawn", new FixedMetadataValue(ROWCORE, true));
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if (p.hasMetadata("goingToSpawn")){
                            p.removeMetadata("goingToSpawn", ROWCORE);

                            p.teleport(ConfigUtils.spawn);
                            p.sendMessage(ChatColor.DARK_AQUA + "Teleportation complete.");

                            p.playSound(p.getLocation(), Sound.valueOf(CONFIG.getString("teleport.sound")), 1, 1);
                        }
                    }
                }.runTaskLater(ROWCORE, 5 * 20);
            }
        }
    }
}
