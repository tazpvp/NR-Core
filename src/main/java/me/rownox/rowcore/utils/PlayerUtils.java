package me.rownox.rowcore.utils;

import me.rownox.rowcore.RowCore;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public final class PlayerUtils {

    /**
     * Check if the player has the given permission or the absolute one.
     * @param p The targeted player
     * @param permission The permission you want to check for
     */
    public static void checkPerms(final Player p, final String permission) {
        if (!p.hasPermission(permission) || !p.hasPermission("rowcore.*")) return;

        p.sendMessage(translateAlternateColorCodes('&', RowCore.getInstance().config.getString("No-Permission") + permission));
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