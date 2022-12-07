package world.ntdi.nrcore.commands;

import world.ntdi.nrcore.utils.ConfigUtils;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;
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
