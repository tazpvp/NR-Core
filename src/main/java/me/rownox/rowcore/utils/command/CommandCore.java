package me.rownox.rowcore.utils.command;

import lombok.NonNull;
import lombok.Setter;
import me.rownox.rowcore.RowCore;
import me.rownox.rowcore.utils.ConfigUtils;
import me.rownox.rowcore.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public abstract class CommandCore extends BukkitCommand {
    private static final Field COMMAND_MAP_FIELD;
    private static final Field KNOWN_COMMANDS_FIELD;

    /**
     * Initializes fields prior to use
     */
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
            throw new RuntimeException("Failed to get command map from plugin manager");
        }
    }

//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        return command(sender, command, label, args);
//    }

    @Setter
    private CommandFunction<? extends CommandSender> defaultFunction;

    /**
     * Generates a command's format
     * @param name Name of the command
     * @param permission Permission for the command
     * @param alias Aliases, can be blank
     */
    public CommandCore(@NonNull final String name, final String permission, @NonNull final String... alias) {
        super(name, "", "/" + name, Arrays.asList(alias));
        setPermission("rowcore."+permission);
        register();
    }

//    public CommandCore(String commandName) {
//        super();
//        this.commandName = commandName;
//
//        RowCore.getInstance().commands.put(this.commandName, this);
//    }

    @SuppressWarnings("unchecked")
    private final void register() {
        try {
            final CommandMap commandMap = (CommandMap) COMMAND_MAP_FIELD.get(Bukkit.getPluginManager());
            final HashMap<String, Command> known = (HashMap<String, org.bukkit.command.Command>) KNOWN_COMMANDS_FIELD
                    .get(commandMap);

            if (commandMap.getCommand(getLabel()) != null) {
                known.remove(getLabel());
            }

            getAliases().forEach(alias -> {
                if (commandMap.getCommand(alias) != null) {
                    known.remove(alias);
                }
            });

            commandMap.register(getLabel(), this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the command
     */
    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (this.getPermission() != null && !sender.hasPermission(getPermission())) {
            sender.sendMessage(invalidPermissionMessage());
        }

        return executeFunction(sender, args, defaultFunction);
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
        return ConfigUtils.noPermission + getPermission();
    }

}
