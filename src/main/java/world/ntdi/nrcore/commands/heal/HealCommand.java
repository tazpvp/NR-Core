package world.ntdi.nrcore.commands.heal;

import lombok.NonNull;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class HealCommand extends NRCommand {
    public static final String NAME = "heal";
    public static final String PERMISSION = "nrcore.heal";
    protected static final String[] ALIASES = { "save" };

    public HealCommand() {
        super(new Label(NAME, PERMISSION, ALIASES));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sendNoPermission(sender);
            return true;
        }

        if (!sender.hasPermission(PERMISSION)) {
            sendNoPermission(sender);
            return true;
        }

        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.setSaturation(20);

        player.sendMessage("Healed");
        return true;
    }
}
