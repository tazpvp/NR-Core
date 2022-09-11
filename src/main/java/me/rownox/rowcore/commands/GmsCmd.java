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

        if (sender instanceof Player p) {

            if (!checkPerms(p,"rowcore.gms")) return false;

            if (args.length >= 1 && args[0] != null) {

                checkPerms(p,"rowcore.gms.others");
                Player target = Bukkit.getPlayer(args[0]);
                target.setGameMode(GameMode.SURVIVAL);

            } else{

                p.setGameMode(GameMode.SURVIVAL);

            }
            return true;
        }
        return false;
    }
}