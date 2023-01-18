package world.ntdi.nrcore.commands;

import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.PlayerUtils;

import java.util.List;

import static world.ntdi.nrcore.utils.PlayerUtils.checkPerms;

public class HealCommandFunction extends CommandCore implements CommandFunction {

    public HealCommandFunction() {
        super("heal", "heal", "heal");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1 && args[0] != null) {
            if (!PlayerUtils.checkPerms(sender, super.getPermission(), ".others")) return;
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) PlayerUtils.healPlr(target);
        } else {
            if (sender instanceof Player p) {
                PlayerUtils.healPlr(p);
            }
        }
    }

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return null;
    }
}
