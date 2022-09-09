package me.rownox.rowcore.utils;

import me.rownox.rowcore.RowCore;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class utilsPlayer {
    public static void checkPerms(Player p, String permission) {

        if (!p.hasPermission(permission) || !p.hasPermission("rowcore.*")) return;

        p.sendMessage(translateAlternateColorCodes('&', RowCore.getInstance().config.getString("No Permission:") + permission));
    }

    public static void healPlr(Player p) {
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        p.setHealth(maxHealth);
        p.setFoodLevel(20);
    }
}