package world.ntdi.nrcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.List;

public class SpeedCommandFunction extends CommandCore implements CommandFunction {

    public SpeedCommandFunction() {
        super("speed", "speed", "sp");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                speed(p, 0);
            } else if (args.length == 1) {
                speed(p, Double.parseDouble(args[0]));
            } else if (args.length == 2) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target != null) {
                    speed(target, Double.parseDouble(args[1]));
                }
            } else {
                p.sendMessage("Usage: /speed <number> <target>");
            }
        }
    }

    public void speed(Player p, double speed){
        if (speed != 0) {
            if (speed <= 10) {
                float a = (float) (speed / 10);
                p.setWalkSpeed(a);
                p.setFlySpeed(a);
                p.sendMessage(ChatColor.GOLD + "Speed: " + ChatColor.RED + speed);
            } else {
                p.sendMessage(ChatColor.RED + "Maximum value: 10");
            }
        } else {
            p.setWalkSpeed((float) 0.2);
            p.setFlySpeed((float) 0.1);
            p.sendMessage(ChatColor.GOLD + "Speed: " + ChatColor.RED + "Default");
        }
    }

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return null;
    }
}
