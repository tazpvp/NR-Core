package me.rownox.rowcore.utils;

import me.rownox.rowcore.RowCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private String commandName;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return command(sender, command, label, args);
    }

    public CommandExecutor(String commandName) {
        this.commandName = commandName;

        RowCore.getInstance().commands.put(commandName, (org.bukkit.command.CommandExecutor) this);
    }

    public abstract boolean command(CommandSender sender, Command command, String label, String[] args);

}
