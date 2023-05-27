package world.ntdi.nrcore.commands.enderchest;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class EnderchestCommand extends NRCommand {
    public static final String NAME = "enderchest";
    public static final String PERMISSION = "nrcore.ec";
    protected static final String[] ALIASES = {"ec", "echest"};

    public EnderchestCommand() {
        super(new Label(NAME, PERMISSION, ALIASES));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (!sender.hasPermission(PERMISSION)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length < 1) {
            p.openInventory(p.getEnderChest());
        } else {
            final String targetName = args[0];
            final Player target = Bukkit.getPlayer(targetName);
            if (target != null) {
                p.openInventory(target.getInventory());
            }
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        }
        return List.of();
    }
}
