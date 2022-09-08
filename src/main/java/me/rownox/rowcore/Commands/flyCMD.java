package me.rownox.rowcore.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.Utils.playerUtils.checkPerms;

public class flyCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            checkPerms(p, "rowcore.fly");
            p.setFlying(true);
        }

        return false;
    }
}
