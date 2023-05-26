package world.ntdi.nrcore.utils.command.simple.builder;

import lombok.Builder;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.bukkit.command.CommandSender;
import world.ntdi.nrcore.utils.command.simple.*;

import java.util.List;

@SuperBuilder
public class NRCommandBuilder {
    protected Label label;
    @Builder.Default
    protected Executor nativeExecutor = (CommandSender sender, String[] args) -> true;
    @Builder.Default
    protected Completer nativeCompleter = (CommandSender sender, String[] args) -> List.of();
    @Singular
    protected List<NRCommand> subcommands;

    public NRCommand make() {
        if (label == null) {
            throw new IllegalStateException("Label cannot be null");
        }

        final NRCommand command = new NRCommand(this.label);
        command.setNativeExecutor(this.nativeExecutor);
        command.setNativeCompleter(this.nativeCompleter);

        for (NRCommand entry : subcommands) {
            command.addSubcommand(entry);
        }

        return command;
    }

    public NRCommand makeAndRegister() {
        final NRCommand command = make();
        BukkitBridge.register(new BukkitBridge(command));
        return command;
    }

    public static NRCommandBuilderBuilder<?, ?> of(Label label) {
        return NRCommandBuilder.builder().label(label);
    }

    public static NRCommandBuilderBuilder<?, ?> of(LabelBuilder label) {
        return NRCommandBuilder.builder().label(label.make());
    }
}
