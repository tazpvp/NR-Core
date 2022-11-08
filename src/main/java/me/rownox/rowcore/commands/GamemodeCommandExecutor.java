package me.rownox.rowcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;

public class GamemodeCommandExecutor implements CommandExecutor {

    private final GameMode gamemode;

    public GamemodeCommandExecutor(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if (gmCheckPerm(GameMode.CREATIVE, "gmc", p)) return false;
            if (gmCheckPerm(GameMode.SURVIVAL, "gms", p)) return false;
            if (gmCheckPerm(GameMode.ADVENTURE, "gma", p)) return false;
            if (gmCheckPerm(GameMode.SPECTATOR, "gmsp", p)) return false;

            if (args.length >= 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (checkPerms(p,"gm.others")) return false;
                if (target != null) target.setGameMode(gamemode);

            } else {
                p.setGameMode(gamemode);
            }
            return true;
        }
        return false;
    }

    public boolean gmCheckPerm(GameMode gm, String node, Player p) {
        if (gamemode.equals(gm)) {
            return checkPerms(p, node);
        }
        return false;
    }
}
