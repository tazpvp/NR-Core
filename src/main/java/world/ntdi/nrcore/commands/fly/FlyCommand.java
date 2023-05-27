package world.ntdi.nrcore.commands.fly;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class FlyCommand extends NRCommand {
    public static final float DEFAULT_FLY_SPEED = 0.1F;

    public FlyCommand() {
        super(new Label("fly", "nrcore.fly"));
        setNativeExecutor((sender, args) -> {
            if (!sender.hasPermission(getLabel().getPermission())) {
                sendNoPermission(sender);
                return true;
            }

            if (!(sender instanceof Player p)) {
                sendNoPermission(sender);
                return true;
            }

            p.setAllowFlight(!p.getAllowFlight());
            p.sendMessage(ChatColor.YELLOW + "Set your flight to: " + p.getAllowFlight());
            return true;
        });
        addSubcommand(new FlyHelpSubCommand());
        addSubcommand(new FlySpeedSubCommand()); // /fly [help, speed]
    }
}
