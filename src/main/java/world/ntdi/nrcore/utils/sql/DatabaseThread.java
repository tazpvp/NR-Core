package world.ntdi.nrcore.utils.sql;

import lombok.Getter;
import org.bukkit.Bukkit;

public class DatabaseThread extends Thread {
    @Getter
    private boolean online = false;

    private Database DB;

    @Override
    public void run() {
        DB = new Database();
        online = true;
        Bukkit.getLogger().info("Online!");
    }

    public Database getDB() {
        if (DB == null) {
            Bukkit.getLogger().severe("We're here again");
        }
        return DB;
    }
}
