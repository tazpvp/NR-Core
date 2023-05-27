package world.ntdi.nrcore.commands.skull;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import java.util.List;

public class SkullCommand extends NRCommand {
    public SkullCommand() {
         super(new Label("skull", "nrcore.skull"));
         addSubcommand(new SkullUUIDCommand());
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sendNoPermission(sender);
            return true;
        }

        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

         if (args.length > 1) {
             if (!args[0].equals("uuid")) {
                 final String targetName = args[0];
                 final Player target = Bukkit.getPlayer(targetName);
                 if (target != null) {
                     player.getInventory().addItem(SkullBuilder.of().setHeadTexture(target.getUniqueId()).build());
                 }
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
