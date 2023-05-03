package world.ntdi.nrcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.List;

public class HelpCommandFunction extends CommandCore implements CommandFunction {
    public HelpCommandFunction() {
        super("help", null, "help");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        NRCore.config.help.forEach(s -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
    }

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return List.of("");
    }
}
