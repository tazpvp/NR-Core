package world.ntdi.nrcore.utils.sql;

import lombok.Getter;
import org.bukkit.Bukkit;

public class DatabaseThread extends Thread {
    @Getter
    private boolean online = false;

    private Database DB;

    public DatabaseThread(Database DB) {
        this.DB = DB;
        online = true;
    }

    @Override
    public void run() {
        while (!online) {
            Bukkit.getLogger().info("Waiting for connection to circumvent checksum h26 file");
        }
    }

    public Database getDB() {
        if (DB == null) {
            Bukkit.getLogger().severe("We're here again");
        }
        return DB;
    }
}
