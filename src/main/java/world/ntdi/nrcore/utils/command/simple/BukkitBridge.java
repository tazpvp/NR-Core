package world.ntdi.nrcore.utils.command.simple;

import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * This class bridges the gap between CommandCL simple packages and Bukkit command
 * classes.
 * <p>
 * This class is not meant to be used by the end user. It is only used
 * internally by CommandCL to bridge the gap between the simple packages and the
 * Bukkit command classes.
 */
public final class BukkitBridge extends BukkitCommand {
    private static final Field COMMAND_MAP_FIELD;
    private static final Field KNOWN_COMMANDS_FIELD;

    static {
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            COMMAND_MAP_FIELD = field;

            field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);
            KNOWN_COMMANDS_FIELD = field;

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to get command map from plugin manager");
        }
    }

    private final NRCommand command;

    public BukkitBridge(NRCommand command) {
        super(command.getLabel().getName(), command.getLabel().getDescription(), command.getLabel().getUsage(),
                command.getLabel().getAliases());
        this.command = command;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return this.command.execute(sender, args);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return this.command.complete(sender, args);
    }

    @SuppressWarnings("unchecked")
    public static void register(@NonNull final BukkitCommand command) {
        try {
            final CommandMap cmap = (CommandMap) COMMAND_MAP_FIELD.get(org.bukkit.Bukkit.getPluginManager());
            final Map<String, Command> knownCommands = (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(cmap);
            if (cmap.getCommand(command.getLabel()) != null) {
                knownCommands.remove(command.getLabel());
            }

            command.getAliases().forEach((String alias) -> {
                if (cmap.getCommand(alias) != null) {
                    knownCommands.remove(alias);
                }
            });

            cmap.register(command.getLabel(), command);

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void unregister(String label) {
        try {
            final CommandMap cmap = (CommandMap) COMMAND_MAP_FIELD.get(org.bukkit.Bukkit.getPluginManager());
            final Map<String, Command> knownCommands = (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(cmap);

            final Command command = cmap.getCommand(label);

            if (command == null) {
                return;
            }

            knownCommands.remove(label);
            command.getAliases().forEach((String alias) -> {
                if (cmap.getCommand(alias) != null) {
                    knownCommands.remove(alias);
                }
            });

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
