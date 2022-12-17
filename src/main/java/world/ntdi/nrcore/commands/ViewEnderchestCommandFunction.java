package world.ntdi.nrcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

public class ViewEnderchestCommandFunction extends CommandCore implements CommandFunction {

    public ViewEnderchestCommandFunction() {
        super("viewec", "tazpvp.viewec", "vec");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    p.openInventory(target.getEnderChest());
                }
            }
        }
    }
}
