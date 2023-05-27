package world.ntdi.nrcore.commands.skull;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import java.util.UUID;

public class SkullUUIDCommand extends NRCommand {
    public SkullUUIDCommand() {
        super(new Label("uuid", "nrcore.skull"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sendNoPermission(sender);
            return true;
        }
        if (args.length >= 1) {
            final String targetUUID = args[0];
            final UUID target = UUID.fromString(targetUUID);
            ItemStack skull = SkullBuilder.of().setHeadTexture(target).build();
            player.getInventory().addItem(skull);
        }
        return true;
    }
}
