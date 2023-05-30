package world.ntdi.nrcore.utils.command.simple;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A Functional Interface that represents a completer for a Command. This
 * is different from the Bukkit completer "TabCompleter" which contains a String
 * label. This is prone to be abused by beginners who tend to not separate their
 * code.
 */
@FunctionalInterface
public interface Completer {
    /**
     * Acts as a completer for an Command.
     *
     * @param sender the sender of the command
     * @param args   all arguments associated with the command
     * @return a list of possible completions
     */
    List<String> complete(CommandSender sender, String[] args);

    static List<String> empty() {
        return List.of();
    }

    static List<String> intRange(int min, int max, int step) {
        List<String> list = new ArrayList<>();
        for (int i = min; i <= max; i += step) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    static List<String> doubleRange(double min, double max, double step) {
        List<String> list = new ArrayList<>();
        for (double i = min; i <= max; i += step) {
            list.add(String.valueOf(Math.round(i * 10)/10));
        }
        return list;
    }

    static List<String> onlinePlayers() {
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player.getName());
        }
        return list;
    }

    static List<String> onlinePlayers(String startsWith) {
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().startsWith(startsWith)) {
                list.add(player.getName());
            }
        }
        return list;
    }

}
