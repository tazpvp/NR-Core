package me.rownox.rowcore.commands.home;

import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.data.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if (args.length >= 1) {
                for (Home homes : RowCore.getInstance().homesList.getHomes()) {

                    if (homes.getUuid() == p.getUniqueId()) {

                        return true;
                    }

                }
                Home home = new Home(p.getUniqueId());
                RowCore.getInstance().homesList.addHome(home);
            }
        }
        return false;
    }
}
