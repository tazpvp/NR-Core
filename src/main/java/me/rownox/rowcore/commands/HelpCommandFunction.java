package me.rownox.rowcore.commands;

import me.rownox.rowcore.utils.ConfigUtils;
import me.rownox.rowcore.utils.command.CommandCore;
import me.rownox.rowcore.utils.command.CommandFunction;
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
