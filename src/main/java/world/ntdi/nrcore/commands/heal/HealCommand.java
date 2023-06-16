package world.ntdi.nrcore.commands.heal;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

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

        Player target = player;
        if (args.length >= 1) {
            target = Bukkit.getPlayer(args[0]);
        }

        for (PotionEffect effect : target.getActivePotionEffects()) {
            target.removePotionEffect(effect.getType());
        }
        target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        target.setFoodLevel(20);
        target.setSaturation(20);

        player.sendMessage("You healed " + target.getName());
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return Completer.onlinePlayers(args[0]);
    }
}
