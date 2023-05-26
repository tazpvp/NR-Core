package world.ntdi.nrcore.utils.command;

import org.bukkit.command.CommandSender;

@Deprecated
public interface CommandFunction<T extends CommandSender> {

    void execute(T sender, String[] args);
}
