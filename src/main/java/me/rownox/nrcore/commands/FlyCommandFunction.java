package me.rownox.nrcore.commands;

import me.rownox.nrcore.utils.command.CommandCore;
import me.rownox.nrcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.nrcore.utils.PlayerUtils.checkPerms;
import static me.rownox.nrcore.utils.PlayerUtils.healPlr;

public class FlyCommandFunction extends CommandCore implements CommandFunction {

    public FlyCommandFunction() {
        super("fly", "rowcore.fly", "flight");
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
