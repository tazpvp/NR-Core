package me.rownox.nrcore.commands;

import me.rownox.nrcore.utils.ConfigUtils;
import me.rownox.nrcore.utils.command.CommandCore;
import me.rownox.nrcore.utils.command.CommandFunction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommandFunction extends CommandCore implements CommandFunction {
    public HelpCommandFunction() {
        super("help", null, "help");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ConfigUtils.help.forEach(s -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
    }
}
