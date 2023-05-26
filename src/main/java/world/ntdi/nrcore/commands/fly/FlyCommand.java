package world.ntdi.nrcore.commands.fly;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.config.ConfigUtils;

public class FlyCommand extends NRCommand {
    public static final String NAME = "fly";
    public static final String PERMISSION = "nrcore.fly";
    public static final float DEFAULT_FLY_SPEED = 0.1F;

    public FlyCommand() {
        super(new Label(NAME, PERMISSION));
        setNativeExecutor((sender, args) -> {
            if (!sender.hasPermission(PERMISSION)) {
                sender.sendMessage(ConfigUtils.PERMISSION_PREFIX + PERMISSION);
                return true;
            }

            if (!(sender instanceof Player p)) {
                sender.sendMessage(ConfigUtils.PERMISSION_PREFIX + PERMISSION);
                return true;
            }

            p.setAllowFlight(!p.getAllowFlight());
            p.sendMessage(ChatColor.YELLOW + "Set your flight to: " + p.getAllowFlight());
            return true;
        });
    }
}
