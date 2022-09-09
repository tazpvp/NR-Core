package me.rownox.rowcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;
import static me.rownox.rowcore.utils.PlayerUtils.healPlr;

public class HealCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) { //sets the person who sent the command to the variable p if they are a player.

            checkPerms(p, "rowcore.heal"); //check permissions

            if (args.length >= 1 && args[0] != null) { //check if they actually have another argument in the command

                checkPerms(p, "rowcore.heal.others");
                Player target = Bukkit.getPlayer(args[0]); //set the target variable to the first argument in the command
                if (target != null) healPlr(target);

            } else {
                healPlr(p);
            }
            return true;
        }
        return false;
    }

}
