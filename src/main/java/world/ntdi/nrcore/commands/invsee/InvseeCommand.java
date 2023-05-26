package world.ntdi.nrcore.commands.invsee;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class InvseeCommand extends NRCommand {
    public InvseeCommand() {
        super(new Label("invsee", "nrcore.invsee"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sendNoPermission(sender, getLabel().getPermission());
            return true;
        }

        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender, getLabel().getPermission());
            return true;
        }

        if (args.length <= 0) {
            sender.sendMessage("No inv to see!");
            return true;
        }

        final String targetName = args[0];
        final Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sender.sendMessage("No target found!!");
            return true;
        }

        player.openInventory(target.getInventory());

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return List.of();
    }
}
