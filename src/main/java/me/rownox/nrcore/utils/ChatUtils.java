package me.rownox.nrcore.utils;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public final class ChatUtils {

    public static String chat(String text) {
        return translateAlternateColorCodes('&', text);
    }
}