package world.ntdi.nrcore.commands;

import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.PlayerUtils;

public class FlyCommandFunction extends CommandCore implements CommandFunction {

    public FlyCommandFunction() {
        super("fly", "fly", "flight");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1 && args[0] != null) {
            if (!PlayerUtils.checkPerms(sender, super.getPermission(), ".others")) return;
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) PlayerUtils.flyPlr(target);
        } else {
            if (sender instanceof Player p) {
                PlayerUtils.flyPlr(p);
            }
        }
    }
}
