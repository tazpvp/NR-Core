package me.rownox.rowcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;

public class GmsCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) { //sets the person who sent the command to the variable p if they are a player.

            checkPerms(p, "rowcore.gms");
            Player target = Bukkit.getPlayer(args[0]); //set the target variable to the first argument in the command

            if (args.length >= 1 && args[0] != null) { //check if they actually have another argument in the command
                target.setGameMode(GameMode.CREATIVE);
            } else {
                p.setGameMode(GameMode.CREATIVE);
            }
        }
        return false;
    }
}