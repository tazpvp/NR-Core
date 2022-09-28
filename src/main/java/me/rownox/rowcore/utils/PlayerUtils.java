package me.rownox.rowcore.utils;

import me.rownox.rowcore.RowCore;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.ChatUtils.chat;

public final class PlayerUtils {

    private static final FileConfiguration CONFIG = RowCore.getInstance().config;

    /**
     * Check if the player has the given permission or the absolute one.
     * @param p The targeted player
     * @param permission The permission you want to check for
     */
    public static boolean checkPerms(final Player p, final String permission) {
        if (p.isOp()) return true;
        if (!p.hasPermission(permission) || !p.hasPermission("rowcore.*")) {
            p.sendMessage(chat(CONFIG.getString("No-Permission") + permission));
            return false;
        }
        return true;
    }

    /**
     * Check if the player has the given permission or the absolute one.
     * @param p The targeted player
     */
    public static boolean checkPerms(final Player p) {
        if (p.isOp()) return true;
        if (!p.hasPermission("rowcore.*")) {
            p.sendMessage(chat(CONFIG.getString("No-Permission") + "rowcore.*"));
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