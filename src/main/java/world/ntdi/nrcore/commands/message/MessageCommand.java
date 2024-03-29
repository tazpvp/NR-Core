package world.ntdi.nrcore.commands.message;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.ChatUtils;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;

public class MessageCommand extends NRCommand {
    public static final WeakHashMap<UUID, UUID> lastMessage = new WeakHashMap<>();

    public MessageCommand() {
        super(new Label("message", null, "dm"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length < 1) {
            return true;
        }

        final String targetName = args[0];
        final Player target = Bukkit.getPlayer(targetName);

        if (target != null) {
            log(player, target, args, 1);
        }

        return true;
    }

    public static void log(Player sender, Player receiver, String[] args, int start) {
        String message = ChatUtils.builder(args, start);

        String formatTo = ChatUtils.chat(
                NRCore.config.SENT_MESSAGE
                        .replace("%player%", receiver.getName())
                        .replace("%msg%", message));
        String formatFrom = ChatUtils.chat(
                NRCore.config.FROM_MESSAGE
                        .replace("%player%", sender.getName())
                        .replace("%msg%", message));

        lastMessage.put(receiver.getUniqueId(), sender.getUniqueId());
        sender.sendMessage(formatTo);
        receiver.sendMessage(formatFrom);

        if (!MessageCommand.lastMessage.containsKey(receiver.getUniqueId())) {
            receiver.sendMessage(ChatColor.YELLOW + "(!) To respond to this message, type /re <message>");
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers();
        }
        return List.of();
    }
}
