package me.rownox.rowcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;
import static me.rownox.rowcore.utils.PlayerUtils.healPlr;

public class FlyCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            if (!checkPerms(p,"fly")) return false;

            if (args.length >= 1 && args[0] != null) {

                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) healPlr(target);

            } else {
                healPlr(p);
            }
            return true;
        }
        return false;
    }

}
