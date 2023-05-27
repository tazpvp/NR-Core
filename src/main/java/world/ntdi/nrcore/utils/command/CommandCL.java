package world.ntdi.nrcore.utils.command;

import lombok.NonNull;
import world.ntdi.nrcore.utils.command.simple.BukkitBridge;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.command.simple.builder.LabelBuilder;
import world.ntdi.nrcore.utils.command.simple.builder.NRCommandBuilder;

public final class CommandCL {
    private CommandCL() {}

    public static void register(@NonNull final NRCommand command) {
        BukkitBridge.register(new BukkitBridge(command));
    }

    public static void register(@NonNull final NRCommand command, @NonNull final String identifier) {
        BukkitBridge.register(new BukkitBridge(command), identifier);
    }

    public static void unregister(@NonNull final String commandName) {
        BukkitBridge.unregister(commandName);
    }

    @SuppressWarnings("unchecked")
    public static <C extends NRCommandBuilder, B extends NRCommandBuilder.NRCommandBuilderBuilder<C, B>> NRCommandBuilder.NRCommandBuilderBuilder<C, B> command(
            @NonNull final Label label) {
        return (NRCommandBuilder.NRCommandBuilderBuilder<C, B>) NRCommandBuilder.of(label);
    }

    @SuppressWarnings("unchecked")
    public static <C extends NRCommandBuilder, B extends NRCommandBuilder.NRCommandBuilderBuilder<C, B>> NRCommandBuilder.NRCommandBuilderBuilder<C, B> command(
            @NonNull final LabelBuilder label) {
        return (NRCommandBuilder.NRCommandBuilderBuilder<C, B>) NRCommandBuilder.of(label);
    }

    @SuppressWarnings("unchecked")
    public static <C extends LabelBuilder, B extends LabelBuilder.LabelBuilderBuilder<C, B>> LabelBuilder.LabelBuilderBuilder<C, B> label(
            @NonNull final String name) {
        return (LabelBuilder.LabelBuilderBuilder<C, B>) LabelBuilder.of(name);
    }

    @SuppressWarnings("unchecked")
    public static <C extends LabelBuilder, B extends LabelBuilder.LabelBuilderBuilder<C, B>> LabelBuilder.LabelBuilderBuilder<C, B> label(
            @NonNull final String name, @NonNull final String permission) {
        return (LabelBuilder.LabelBuilderBuilder<C, B>) LabelBuilder.of(name, permission);
    }
}
