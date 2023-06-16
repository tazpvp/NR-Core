package world.ntdi.nrcore.commands.gamemode;

import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.command.simple.builder.NRCommandBuilder;

public enum GameModeCommand {
    GMC_COMMAND(NRCommandBuilder.of(new Label("gmc", "nrcore.gamemode"))
            .nativeExecutor(new GameModeCommandExecutor(org.bukkit.GameMode.CREATIVE, "Creative", "nrcore.gamemode"))
            .build()
            .make()),
    GMS_COMMAND(NRCommandBuilder.of(new Label("gms", "nrcore.gamemode"))
            .nativeExecutor(new GameModeCommandExecutor(org.bukkit.GameMode.SURVIVAL, "Survival", "nrcore.gamemode"))
            .build()
            .make()),
    GMSP_COMMAND(NRCommandBuilder.of(new Label("gmsp", "nrcore.gamemode"))
            .nativeExecutor(new GameModeCommandExecutor(org.bukkit.GameMode.SPECTATOR, "Spectator", "nrcore.gamemode"))
            .build()
            .make()),
    GMA_COMMAND(NRCommandBuilder.of(new Label("gma", "nrcore.gamemode"))
            .nativeExecutor(new GameModeCommandExecutor(org.bukkit.GameMode.ADVENTURE, "Adventure", "nrcore.gamemode"))
            .build()
            .make());

    private final NRCommand command;

    GameModeCommand(final NRCommand command) {
        this.command = command;
    }

    public NRCommand command() {
        return command;
    }
}
