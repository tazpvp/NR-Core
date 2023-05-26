package world.ntdi.nrcore.commands.fly;

import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class FlyHelpSubCommand  extends NRCommand { // /fly help

    public static final String NAME = "help";
    public static final String PERMISSION = "nrcore.fly";

    public FlyHelpSubCommand() {
        super(new Label(NAME, PERMISSION));
        setNativeExecutor((sender, args) -> {
            sender.sendMessage("/fly (speed [number]|help)");
            return true;
        });
    }

}
