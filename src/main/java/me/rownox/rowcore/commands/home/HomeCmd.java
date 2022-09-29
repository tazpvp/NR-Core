package me.rownox.rowcore.commands.home;

import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.data.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HomeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        RowCore rowcore = RowCore.getInstance();

        if (sender instanceof Player p) {
            if (args.length >= 1) {
                String homeName = args[0];
                Home home = rowcore.homesList.getHome(p.getUniqueId());
                if (home != null) {
                    for (String pHome : home.getHomes().keySet()) {
                        if (pHome.equals(homeName)) { //if p has the home
                            p.teleport(home.getHomes().get(homeName));
                        }
                    }
                } else {
                    p.sendMessage("you aint got no homes IDIOT");
                }
            }

        }

        return false;
    }
}
