package me.rownox.rowcore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

import static me.rownox.rowcore.Utils.playerUtils.checkPerms;

public class killCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            checkPerms(p, "rowcore.kill");
            //sets the person who sent the command to the variable p if they are a player.
            Player target = Bukkit.getPlayer(args[0]);
            target.setHealth(0);
            //sets health to 0 i think
        }


        return false;
    }

}
