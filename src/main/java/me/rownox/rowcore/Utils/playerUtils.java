package me.rownox.rowcore.Utils;

import org.bukkit.entity.Player;

public class playerUtils {
    public static void checkPerms(Player p, String permission) {
        if (!p.hasPermission(permission) || !p.hasPermission("rowcore.*")) return;
    }
}
