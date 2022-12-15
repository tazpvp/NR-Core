package world.ntdi.nrcore.utils;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public final class ChatUtils {

    public static String chat(String text) {
        return translateAlternateColorCodes('&', text);
    }

    /**
     * <a href="https://www.baeldung.com/java-remove-last-character-of-string">...</a>
     * @param s String to affect
     * @return The new string without the last character
     */
    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }
}