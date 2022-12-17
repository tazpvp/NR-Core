package world.ntdi.nrcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.ChatUtils;
import world.ntdi.nrcore.utils.ConfigUtils;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.UUID;
import java.util.WeakHashMap;

public class MessageCommandFunction extends CommandCore implements CommandFunction {
    private final WeakHashMap<UUID, UUID> lastMessage = new WeakHashMap<>();

    public MessageCommandFunction() {
        super("dm", null, "re");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length < 1) {
                return;
            }

            Player plr = Bukkit.getPlayer(args[0]);
            if (plr == null) {
                if (!lastMessage.containsKey(p.getUniqueId())) {
                    return;
                }
                plr = Bukkit.getPlayer(lastMessage.get(p.getUniqueId()));
                if (plr != null) {
                    log(p, plr, args, 0);
                    return;
                }
                return;
            }

            log(p, plr, args, 1);
        }
    }

    private void log(Player sender, Player receiver, String[] args, int start) {
        String message = ChatUtils.builder(args, start);

        String formatTo = ChatUtils.chat(
                ConfigUtils.SENT_MESSAGE
                        .replace("%player%", receiver.getName())
                        .replace("%msg%", message));
        String formatFrom = ChatUtils.chat(
                ConfigUtils.FROM_MESSAGE
                        .replace("%player%", sender.getName())
                        .replace("%msg%", message));

        lastMessage.put(receiver.getUniqueId(), sender.getUniqueId());
        sender.sendMessage(formatTo);
        receiver.sendMessage(formatFrom);
    }
}
