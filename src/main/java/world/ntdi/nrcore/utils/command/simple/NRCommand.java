package world.ntdi.nrcore.utils.command.simple;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import world.ntdi.nrcore.utils.config.ConfigUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
// not in a multi-threaded environment for commands nor woudl that be wanted
@SuppressWarnings("java:S3366")
public class NRCommand implements Completer, Executor {
    @Getter
    private final Label label;
    @Setter
    private Executor nativeExecutor;
    @Setter
    private Completer nativeCompleter;
    private final Map<String, NRCommand> subcommands;

    public NRCommand(@NonNull Label label) {
        this.label = label;
        this.nativeExecutor = null;
        this.nativeCompleter = null;
        this.subcommands = new HashMap<>();
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 0) {
            boolean result = true;
            if (this.nativeExecutor != null) {
                result = this.nativeExecutor.execute(sender, args);
            }
            return result;
        }

        final NRCommand sub = subcommands.getOrDefault(args[0], null);
        if (sub == null) {
            return true;
        }

        final String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        return sub.execute(sender, subArgs);
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {

        if (nativeCompleter != null) {
            return nativeCompleter.complete(sender, args);
        }

        if (args.length == 1) {
            return this.subcommands.keySet().stream().filter((String s) -> {
                final NRCommand sub = subcommands.get(s);
                if (sub.getLabel().getPermission() == null) {
                    return true;
                }

                return sender.hasPermission(sub.getLabel().getPermission());
            }).toList();
        }

        final NRCommand sub = subcommands.getOrDefault(args[0], null);
        if (sub == null) {
            return List.of();
        }

        if (sub.getLabel().getPermission() != null && !sender.hasPermission(sub.getLabel().getPermission())) {
            return List.of();
        }

        final String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        return sub.complete(sender, subArgs);
    }

    public void addSubcommand(@NonNull NRCommand subcommand) {

        String name = this.findOverlappingSubcommand(subcommand.getLabel().getName());
        if (name != null) {
            throw new IllegalArgumentException("Subcommand '" + name + "' already exists");
        }

        name = this.findOverlappingSubcommand(subcommand.getLabel().getAliases().toArray(String[]::new));
        if (name != null) {
            throw new IllegalArgumentException("Subcommand with alias '" + name + "' already exists for subcommand '"
                    + subcommand.getLabel().getName() + "'");
        }

        this.subcommands.put(subcommand.getLabel().getName(), subcommand);
        for (String alias : subcommand.getLabel().getAliases()) {
            this.subcommands.put(alias, subcommand);
        }
    }

    public void removeSubcommand(@NonNull final String name) {
        if (this.subcommands.containsKey(name)) {
            NRCommand subcommand = this.subcommands.get(name);
            this.subcommands.remove(name);
            for (String alias : subcommand.getLabel().getAliases()) {
                this.subcommands.remove(alias);
            }
        }
    }

    private final String findOverlappingSubcommand(@NonNull final String... aliases) {
        for (String alias : aliases) {
            if (this.subcommands.containsKey(alias)) {
                return alias;
            }
        }
        return null;
    }

    protected void sendNoPermission(CommandSender sender) {
        sender.sendMessage(getLabel().getPermission());
    }

    protected void sendIncorrectUsage(CommandSender sender, String correctUsage) {
        sender.sendMessage(ConfigUtils.incorrectUsage + correctUsage);
    }

    protected void sendIncorrectUsage(CommandSender sender) {
        sendIncorrectUsage(sender, "");
    }


}
