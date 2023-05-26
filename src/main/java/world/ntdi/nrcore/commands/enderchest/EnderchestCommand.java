package world.ntdi.nrcore.commands.enderchest;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

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
            sendNoPermission(sender, PERMISSION);
            return true;
        }

        if (!sender.hasPermission(PERMISSION)) {
            sendNoPermission(sender, PERMISSION);
            return true;
        }

        p.openInventory(p.getEnderChest());
        return true;
    }
}
