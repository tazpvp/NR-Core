package me.rownox.nrcore.commands;

import me.rownox.nrcore.utils.command.CommandCore;
import me.rownox.nrcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.nrcore.utils.PlayerUtils.checkPerms;
import static me.rownox.nrcore.utils.PlayerUtils.healPlr;

public class HealCommandFunction extends CommandCore implements CommandFunction {

    public HealCommandFunction() {
        super("heal", "heal", "heal");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1 && args[0] != null) {
            if (!checkPerms(sender, super.getPermission(), ".others")) return;
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) healPlr(target);
        } else {
            if (sender instanceof Player p) {
                healPlr(p);
            }
        }
    }
}
