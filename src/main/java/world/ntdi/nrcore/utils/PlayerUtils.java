package world.ntdi.nrcore.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.NRCore;

public final class PlayerUtils {

    private static final FileConfiguration CONFIG = NRCore.getInstance().getConfig();
    private static final String PREFIX = "nrcore.";

    public static boolean checkPerms(final CommandSender sender, final String arg1, final String arg2) {
        if (sender.isOp()) return true;
        if (!sender.hasPermission(arg1 + arg2) && !sender.hasPermission(PREFIX + "*")) {
            sender.sendMessage(ChatUtils.chat(NRCore.config.noPermission + PREFIX + arg1 + arg2));
            return false;
        }
        return true;
    }

    /**
     * Check if the player has the given permission or the absolute one.
     * @param sender The targeted Command Sender
     * @param permission The permission you want to check for
     */
    public static boolean checkPerms(final CommandSender sender, final String permission) {
        if (sender.isOp()) return true;
        if (!sender.hasPermission(permission) && !sender.hasPermission(PREFIX + "*")) {
            sender.sendMessage(ChatUtils.chat(NRCore.config.noPermission + PREFIX + permission));
            return false;
        }
        return true;
    }

    /**
     * Check if the player has the given permission or the absolute one.
     * @param sender The targeted Command Sender
     */
    public static boolean checkPerms(final CommandSender sender) {
        if (sender.isOp()) return true;
        if (!sender.hasPermission(PREFIX + "*")) {
            sender.sendMessage(ChatUtils.chat(NRCore.config.noPermission + PREFIX + "*"));
            return false;
        }
        return true;
    }

    /**
     * Heal a player and set their hunger to full.
     * @param p The targeted player
     */
    public static void healPlr(final Player p) {
        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        p.setFoodLevel(20);
    }

    public static void flyPlr(final Player p) {
        p.setFlying(true);
    }
}