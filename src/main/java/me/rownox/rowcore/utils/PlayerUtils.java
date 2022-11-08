package me.rownox.rowcore.utils;

import me.rownox.rowcore.RowCore;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.ChatUtils.chat;

public final class PlayerUtils {

    private static final FileConfiguration CONFIG = RowCore.getInstance().config;
    private static final String prefix = "rowcore.";

    /**
     * Check if the player has the given permission or the absolute one.
     * @param sender The targeted Command Sender
     * @param permission The permission you want to check for
     */
    public static boolean checkPerms(final CommandSender sender, final String permission) {
        if (sender.isOp()) return true;
        if (!sender.hasPermission(permission) && !sender.hasPermission(prefix + "*")) {
            sender.sendMessage(chat(ConfigUtils.noPermission + permission));
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
        if (!sender.hasPermission(prefix + "*")) {
            sender.sendMessage(chat(ConfigUtils.noPermission + prefix + "*"));
            return false;
        }
        return true;
    }

    /**
     * Heal a player and set their hunger to full.
     * @param p The targeted player
     */
    public static void healPlr(final Player p) {
        final double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        p.setHealth(maxHealth);
        p.setFoodLevel(20);
    }
}