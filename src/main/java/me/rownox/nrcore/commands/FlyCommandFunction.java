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
        super("fly", ".fly", "flight");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
