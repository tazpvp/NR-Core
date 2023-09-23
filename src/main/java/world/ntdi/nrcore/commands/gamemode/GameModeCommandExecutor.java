package world.ntdi.nrcore.commands.gamemode;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Executor;

@RequiredArgsConstructor
public class GameModeCommandExecutor implements Executor {
    private final GameMode gamemode;
    private final String gameModeName;
    private final String permission;

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        if (!sender.hasPermission(permission)) {
            sender.sendMessage("No permission");
            return true;
        }

        Player target;

        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "That is not a player.");
                return true;
            }
        } else {
            target = player;
        }

        target.setGameMode(gamemode);
        player.sendMessage(ChatColor.DARK_AQUA + "" + target + "'s Gamemode: " + ChatColor.AQUA + gameModeName);
        return true;

    }
}
