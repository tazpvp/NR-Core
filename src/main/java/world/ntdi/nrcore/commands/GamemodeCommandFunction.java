package world.ntdi.nrcore.commands;

import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.PlayerUtils;

import javax.annotation.Nonnull;

import java.util.List;

import static world.ntdi.nrcore.utils.PlayerUtils.checkPerms;

public class GamemodeCommandFunction extends CommandCore implements CommandFunction {

    private final GameMode GAMEMODE;
    private final String NODE;

    /**
     * Create a new gamemode command instance,
     * @param GAMEMODE The gamemode you want the player to change to
     * @param NODE The alias of the gamemode command e.g. "gmc"
     */
    public GamemodeCommandFunction(@Nonnull final GameMode GAMEMODE, @Nonnull final String NODE) {
        super(NODE, NODE, NODE);
        this.GAMEMODE = GAMEMODE;
        this.NODE = NODE;
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length >= 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (!PlayerUtils.checkPerms(p, NODE, ".others")) return;
                if (target != null) {
                    target.setGameMode(GAMEMODE);
                    target.sendMessage("Gamemode: " + GAMEMODE.toString().toUpperCase());
                    p.sendMessage(target.getName() + "'s gamemode: " + GAMEMODE.toString().toUpperCase());
                }
            } else {
                p.setGameMode(GAMEMODE);
                p.sendMessage("Gamemode: " + GAMEMODE.toString().toUpperCase());
            }
        }
    }

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return null;
    }
}
