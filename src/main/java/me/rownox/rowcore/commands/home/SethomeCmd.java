package me.rownox.rowcore.commands.home;

import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.data.Home;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if (args.length >= 1) {
                String name = args[0];
                if (RowCore.getInstance().homesList.getHome(p.getUniqueId()) != null) {
                    makeNewHome(p, name);
                } else {
                    Home home = new Home(p.getUniqueId());
                    RowCore.getInstance().homesList.addHome(home);
                    makeNewHome(p, name);
                }
                return true;
            }
        }
        return false;
    }

    private void makeNewHome(Player p, String name) {
        Location location = p.getLocation();
        Home home = RowCore.getInstance().homesList.getHome(p.getUniqueId());
        RowCore.getInstance().homesList.removeHome(home);
        home.addHome(name, location);
        RowCore.getInstance().homesList.addHome(home);
        p.sendMessage("Made Home");
    }
}
