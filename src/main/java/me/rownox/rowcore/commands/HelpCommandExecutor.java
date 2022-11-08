package me.rownox.rowcore.commands;

import lombok.NonNull;
import me.rownox.rowcore.utils.ConfigUtils;
import me.rownox.rowcore.utils.command.CommandCore;
import me.rownox.rowcore.utils.command.CommandFunction;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HelpCommandExecutor extends CommandCore implements CommandFunction {
    public HelpCommandExecutor() {
        super("help", null, "help");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ConfigUtils.help.forEach(s -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
    }
}
