package world.ntdi.nrcore.utils.scoreboard.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

@Getter
public abstract class NRScoreboard {
    private final Scoreboard scoreboard;
    private final Objective objective;
    private int spaceCounter;
    private int lineCounter;

    public NRScoreboard(final String title) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("statboard", "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        spaceCounter = 1;
        lineCounter = 15;

        initialize();
    }

    public void setScoreboard(final Player player) {
        player.setScoreboard(getScoreboard());
    }

    private Team updateLine(final String name) {
        return scoreboard.getTeam(name);
    }

    private void updateLinePrefix(final String name, final String prefix) {
        updateLine(name).setPrefix(prefix);
    }

    private void updateLineSuffix(final String name, final String suffix) {
        updateLine(name).setSuffix(suffix);
    }

    private Score newLine(final String name, final String prefix, final String suffix) {
        final String ID = UUID.randomUUID().toString();
        final Team team = scoreboard.registerNewTeam(name);

        team.addEntry(ID);
        team.setPrefix(prefix);
        team.setSuffix(suffix);

        return objective.getScore(ID);
    }

    public void addLine(final String name, final String prefix, final String suffix) {
        newLine(name, prefix, suffix).setScore(decreaseLineCount());
    }

    public void addBlackLine() {
        objective.getScore(new String(new char[spaceCounter]).replace('\0', ' ')).setScore(decreaseLineCount());
    }

    private int decreaseLineCount() {
        if (lineCounter < 1) {
            throw new IndexOutOfBoundsException("Too many scoreboard lines");
        }

        final int temp = lineCounter;
        lineCounter -= 1;

        return temp;
    }

    public abstract void initialize();

    public void setDisplayHealthUnderName(final boolean value) {
        if (value) {
            if (scoreboard.getObjective("health") == null) {
                Objective healthObjective = scoreboard.registerNewObjective("health", Criteria.HEALTH, ChatColor.RED + "❤", RenderType.INTEGER);
                healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
        } else {
            if (scoreboard.getObjective("health") != null) {
                Objective health = scoreboard.getObjective("health");
                health.unregister();
            }
        }
    }
}
