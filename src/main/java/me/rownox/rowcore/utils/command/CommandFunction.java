package me.rownox.rowcore.utils.command;

import org.bukkit.command.CommandSender;

public interface CommandFunction<T extends CommandSender> {

    void execute(T sender, String[] args);
}
