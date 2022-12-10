package world.ntdi.nrcore.utils.nametag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import world.ntdi.nrcore.utils.ChatUtils;

import javax.annotation.Nullable;

public class PlayerNameTag {

    private char[] ranking = new char[]
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Initialize a Player's overhead name
     * @param p The player
     * @param ranking Player's tablist sorting
     * @param prefix Player's prefix (Nullable)
     * @param suffix Player's suffix (Nullable)
     * @param color Player's color (Nullable)
     */
    public void initializePlayerNameTag(Player p, int ranking, @Nullable String prefix, @Nullable String suffix, @Nullable ChatColor color) {
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            updateScoreboard(ranking, prefix, suffix, color, p, otherPlayer);
            updateScoreboard(ranking, prefix, suffix, color, otherPlayer, p);
        }
    }


    private void updateScoreboard(int rank, @Nullable String prefix, @Nullable String suffix, @Nullable ChatColor color, Player p1, Player p2) {
        String teamName = getRankRanking(rank) + p2.getUniqueId();
        Team team = p1.getScoreboard().getTeam(teamName);
        if (team != null) {
            team.unregister();
        }

        team = p1.getScoreboard().registerNewTeam(teamName);

        team.setDisplayName(p2.getName());

        if (prefix != null) team.setPrefix(ChatUtils.chat(prefix));
        if (suffix != null) team.setSuffix(ChatUtils.chat(suffix));
        if (color != null) team.setColor(color);

        team.addPlayer(p2);
    }

    /**
     * For sorting tab list which ranks
     * @param rank Ranking number
     * @return Ranked value string
     */
    private String getRankRanking(final int rank) {
        int count = 26 / rank;
        String value = "";
        for (int i = 0; i < count; i++) {
            value = value + ranking[i];
        }
        return value;
    }
}
