package me.rownox.rowcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.utilsPlayer.checkPerms;

public class cmdFly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            checkPerms(p, "rowcore.fly");
            p.setFlying(true);
        }

        return false;
    }

}
