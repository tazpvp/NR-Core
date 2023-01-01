package world.ntdi.nrcore.commands;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

public class InvseeCommandFunction extends CommandCore implements CommandFunction {

    public InvseeCommandFunction() {
        super("invsee", "invsee", "openinv");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    p.openInventory(target.getInventory());
                    NRCore.invsee.add(p.getUniqueId());
                }
            } else {
                p.sendMessage("Usage: /invsee <player>");
            }
        }
    }
}
