package world.ntdi.nrcore.commands.message;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class ReplyCommand extends NRCommand {
    public ReplyCommand() {
        super(new Label("reply", null, "re", "r"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length > 0) {
            if (MessageCommand.lastMessage.containsKey(p.getUniqueId())) {
                final Player target = Bukkit.getPlayer(MessageCommand.lastMessage.get(p.getUniqueId()));
                if (target != null) {
                    MessageCommand.log(p, target, args, 0);
                }
            }
        }

        return true;
    }
}
