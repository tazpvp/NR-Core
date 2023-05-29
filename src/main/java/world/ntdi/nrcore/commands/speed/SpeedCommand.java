package world.ntdi.nrcore.commands.speed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class SpeedCommand extends NRCommand {
    public SpeedCommand() {
        super(new Label("speed", "nrcore.speed"));
        setNativeExecutor((sender, args) -> {

            if (!sender.hasPermission(getLabel().getPermission())) {
                sendNoPermission(sender);
                return true;
            }

            if (!(sender instanceof Player p)) {
                sendNoPermission(sender);
                return true;
            }

            if (args.length < 1) {
                p.setWalkSpeed(0.2F);
                return true;
            }

            final double speed = Integer.parseInt(args[0]);

            if (speed <= 10) {
                float a = (float) (speed / 10);
                p.setWalkSpeed(a);
                p.sendMessage(ChatColor.GOLD + "Speed: " + ChatColor.RED + speed);
            } else {
                p.sendMessage(ChatColor.RED + "Maximum value: 10");
            }

            return true;
        });
    }
}
