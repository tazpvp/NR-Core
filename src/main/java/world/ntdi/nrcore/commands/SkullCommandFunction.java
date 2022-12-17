package world.ntdi.nrcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import java.util.UUID;

public class SkullCommandFunction extends CommandCore implements CommandFunction {
    public SkullCommandFunction() {
        super("skull", "skull", "getskull");
        setUsage("/skull <uuid|player> <value>");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 1) {
                String val = args[1];
                if (args[0].equals("uuid")) {
                    UUID uuid;
                    try {
                        uuid = UUID.fromString(val);
                    } catch (IllegalArgumentException e) {
                        p.sendMessage(ChatColor.RED + "Invalid UUID");
                        return;
                    }
                    p.getInventory().addItem(SkullBuilder.of().setHeadTexture(uuid).build());
                } else {
                    Player target = Bukkit.getPlayer(val);
                    if (target != null) {
                        p.getInventory().addItem(SkullBuilder.of(1, ChatColor.YELLOW + target.getName()).setHeadTexture(target).build());
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid target");
                    }
                }
            }
        }
    }
}
