package me.rownox.rowcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            p.sendMessage(command.getName());
            p.sendMessage(label);
            p.sendMessage(command.getLabel());
            return true;
        }
        return false;
    }
}
