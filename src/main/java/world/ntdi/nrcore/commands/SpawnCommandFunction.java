package world.ntdi.nrcore.commands;

import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.events.MoveEvent;
import world.ntdi.nrcore.utils.config.ConfigUtils;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.PlayerUtils;

import java.util.List;

import static world.ntdi.nrcore.utils.PlayerUtils.checkPerms;

public class SpawnCommandFunction extends CommandCore implements CommandFunction {
    private final String spawnType;

    public SpawnCommandFunction(String type) {
        super(type+"spawn", null, type+"spawn");
        this.spawnType = type;
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        final NRCore ROWCORE = NRCore.getInstance();
        final FileConfiguration CONFIG = NRCore.getInstance().config;

        if (args.length >= 1) {
            if (!PlayerUtils.checkPerms(sender, "spawn", ".others")) return;
            Player target = Bukkit.getPlayer(args[0]);
            target.teleport(ConfigUtils.spawn);
            target.playSound(target.getLocation(), Sound.valueOf(CONFIG.getString("teleport.sound")), 1, 1);

        }

        if (sender instanceof Player p) {
            if (spawnType.equals("set")) {
                if (!PlayerUtils.checkPerms(p,"spawn.set")) return;

                Location loc = p.getLocation();
                ConfigUtils.setSpawn(loc);

            } else {
                if (PlayerUtils.checkPerms(p,"spawn.bypass")) {
                    p.teleport(ConfigUtils.spawn);
                    p.sendMessage(ChatColor.DARK_AQUA + "Teleportation complete.");
                } else {
                    p.sendMessage(ChatColor.DARK_AQUA + "You'll be teleported to spawn in " + ChatColor.AQUA + CONFIG.getString("teleport.delay") + " Seconds" + ChatColor.DARK_AQUA + " Do not move.");
                    p.setMetadata("goingToSpawn", new FixedMetadataValue(ROWCORE, true));
                    MoveEvent.loc.put(p.getUniqueId(), p.getLocation());

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

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return null;
    }
}
