package world.ntdi.nrcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

public class SpeedCommandFunction extends CommandCore implements CommandFunction {

    public SpeedCommandFunction() {
        super("speed", "speed", "sp");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 1) {
                int speed = Integer.parseInt(args[0]);
                p.setWalkSpeed(speed);
            }
        }
    }
}
