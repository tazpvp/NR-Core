package world.ntdi.nrcore.utils.command;

import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.SimplePluginManager;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.config.ConfigUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Deprecated
public abstract class CommandCore extends BukkitCommand implements TabCompleter {
    private static final Field COMMAND_MAP_FIELD;
    private static final Field KNOWN_COMMANDS_FIELD;

    /**
     * Initializes command fields with reflection prior to use
     */
    static {
        try { //Command Map field, private -> public
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            COMMAND_MAP_FIELD = field;

            field = SimpleCommandMap.class.getDeclaredField("knownCommands"); // Known commands field, private -> public
            field.setAccessible(true);
            KNOWN_COMMANDS_FIELD = field;

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get command map from plugin manager");
        }
    }

    @Setter
    private CommandFunction<? extends CommandSender> defaultFunction;

    /**
     * Generates a command's format
     * @param name Name of the command
     * @param permission Permission for the command
     * @param alias Aliases, can be blank
     */
    public CommandCore(@NonNull final String name, final String permission, final String... alias) {
        super(name, "", "/" + name, Arrays.asList(alias));
        setPermission(ConfigUtils.PERMISSION_PREFIX+permission);
        register();
    }

    /**
     * Register the command into the known command map for Bukkit
     */
    @SuppressWarnings("unchecked")
    private final void register() {
        try {
            final CommandMap commandMap = (CommandMap) COMMAND_MAP_FIELD.get(Bukkit.getPluginManager()); // Get fields
            final HashMap<String, Command> known = (HashMap<String, org.bukkit.command.Command>) KNOWN_COMMANDS_FIELD
                    .get(commandMap); // Get known commands

            // Remove old labels
            if (commandMap.getCommand(getLabel()) != null) {
                known.remove(getLabel());
            }

            // Remove old aliases
            getAliases().forEach(alias -> {
                if (commandMap.getCommand(alias) != null) {
                    known.remove(alias);
                }
            });

            // Re-register updated information
            commandMap.register(getLabel(), this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public abstract List<String> tabCompletion(CommandSender sender, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return tabCompletion(sender, args);
    }

    /**
     * Called when the command is sent in minecraft
     * @param sender Source object which is executing this command
     * @param commandLabel The alias of the command used
     * @param args All arguments passed to the command, split via ' '
     * @return
     */
    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (this.getPermission() != null && !sender.hasPermission(getPermission())) { // Checks for perms
            sender.sendMessage(invalidPermissionMessage());
        }

        return executeFunction(sender, args, defaultFunction); // Handles reflection
    }

    public boolean executeCommand(@NonNull final CommandSender sender, @NonNull final String[] args,
                                  @NonNull final CommandFunction<? extends CommandSender> function) {
        return executeFunction(sender, args, function);
    }

    /**
     * Uses reflection to execute a function
     *
     * @param sender   the sender
     * @param args     the arguments
     * @param function the function
     * @return
     */
    private final boolean executeFunction(@NonNull final CommandSender sender, @NonNull final String[] args,
                                          @NonNull final CommandFunction<? extends CommandSender> function) {
        tabComplete(sender, getName(), tabCompletion(sender, args).toArray(String[]::new));
        try {
            final Method method = function.getClass().getMethod("execute", CommandSender.class, String[].class);
            method.invoke(function, sender, args);
            return true;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String invalidPermissionMessage() {
        return NRCore.config.noPermission + getPermission();
    }

}
