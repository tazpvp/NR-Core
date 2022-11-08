package me.rownox.rowcore.commands;

import me.rownox.rowcore.utils.command.CommandCore;
import me.rownox.rowcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;

public class GamemodeCommandExecutor extends CommandCore implements CommandFunction {

    private final GameMode GAMEMODE;
    private final String NODE;

    /**
     * Create a new gamemode command instance,
     * @param GAMEMODE The gamemode you want the player to change to
     * @param NODE The alias of the gamemode command e.g. "gmc"
     */
    public GamemodeCommandExecutor(@Nonnull final GameMode GAMEMODE, @Nonnull final String NODE) {
        super(NODE, "gm", NODE);
        this.GAMEMODE = GAMEMODE;
        this.NODE = NODE;
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
//            if (gmCheckPerm(GameMode.CREATIVE, "gmc", p)) return;
//            if (gmCheckPerm(GameMode.SURVIVAL, "gms", p)) return;
//            if (gmCheckPerm(GameMode.ADVENTURE, "gma", p)) return;
//            if (gmCheckPerm(GameMode.SPECTATOR, "gmsp", p)) return;
            if (args.length >= 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (checkPerms(p,"gm.others")) return;
                if (target != null) target.setGameMode(GAMEMODE);
            } else {
                p.setGameMode(GAMEMODE);
            }
        }
    }

    //TODO: Figure out wtf this does -Ntdi
    public boolean gmCheckPerm(GameMode gm, String node, Player p) {
        if (GAMEMODE.equals(gm)) {
            return checkPerms(p, node);
        }
        return false;
    }
}
