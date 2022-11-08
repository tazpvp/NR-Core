package me.rownox.rowcore.commands;

import lombok.NonNull;
import me.rownox.rowcore.utils.command.CommandCore;
import me.rownox.rowcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;
import static me.rownox.rowcore.utils.PlayerUtils.healPlr;

public class FlyCommandExecutor extends CommandCore implements CommandFunction {

    public FlyCommandExecutor() {
        super("fly", "fly", "flight");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length >= 1 && args[0] != null) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) healPlr(target);
            } else {
                healPlr(p);
            }
        }
    }
}