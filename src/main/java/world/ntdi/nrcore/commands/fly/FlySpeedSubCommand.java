package world.ntdi.nrcore.commands.fly;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class FlySpeedSubCommand extends NRCommand {
    public static final String NAME = "speed";
    public static final String PERMISSION = "nrcore.fly.speed";

    private static final float MAGIC_INVALID_NUMBER = Float.NaN;

    public FlySpeedSubCommand() {
        super(new Label(NAME, PERMISSION));
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

        float speed;
        if (args.length == 0) {
            speed = FlyCommand.DEFAULT_FLY_SPEED;
        } else if (args.length >= 1) {
            speed = verifyNumber(sender, args[0]);
            if (speed == MAGIC_INVALID_NUMBER) {
                return true;
            }
        } else {
            sender.sendMessage("/fly");
            return true;
        }

        player.setFlySpeed(speed);
        sender.sendMessage("Set flyspeed to " + speed);
        return true;
    }

    private float verifyNumber(CommandSender sender, String arg) {
        try {
            return Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            sender.sendMessage("/fly");
            return MAGIC_INVALID_NUMBER;
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.doubleRange(0.1, 0.9, 0.1);
        }
        return List.of();
    }
}
