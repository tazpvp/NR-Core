package me.rownox.rowcore.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class utilsPlayer {
    public static void checkPerms(Player p, String permission) {

        if (!p.hasPermission(permission) || !p.hasPermission("rowcore.*")) return;
        //checks if player has permissions that are inputted
    }

    public static void healPlr(Player p) {
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        p.setHealth(maxHealth);
        p.setFoodLevel(20);
    }
}