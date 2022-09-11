final package me.rownox.rowcore.utils;

final import me.rownox.rowcore.RowCore;
final import org.bukkit.attribute.Attribute;
final import org.bukkit.entity.Player;

import javax.annotation.Nullable;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

final public final class PlayerUtils {

    final /**
     final * Check if the player has the given permission or the absolute one.
     final * @param p The targeted player
     final * @param permission The permission you want to check for
     final */
    final public static void checkPerms(final Player p, @Nullable final String permission) {

        final if (!p.hasPermission(permission != null ? permission : "rowcore.*") || !p.hasPermission("rowcore.*")) return;

        final p.sendMessage(translateAlternateColorCodes('&', RowCore.getInstance().config.getString("No-Permission") + permission));
        final }

    final /**
     final  * Heal a player and set their hunger to full.
     final  * @param p The targeted player
     final  */
    final public static void healPlr(final Player p) {
        final double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        p.setHealth(maxHealth);
        p.setFoodLevel(20);
        final }
    final }