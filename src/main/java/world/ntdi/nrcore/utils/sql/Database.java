package world.ntdi.nrcore.utils.sql;

import lombok.Getter;
import org.bukkit.Bukkit;
import world.ntdi.nrcore.NRCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    @Getter
    private Connection c = null;
    @Getter
    private Statement stmt = null;

    public Database() {
        if (NRCore.config.SQLENABLED) {
            Bukkit.getLogger().info("Attempting to connect to Postgresql Database");
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection(
                        "jdbc:postgresql://" + NRCore.config.SQLURL + "/postgres",
                        NRCore.config.SQLUSER, NRCore.config.SQLPASSWORD
                );
                stmt = c.createStatement();
                Bukkit.getLogger().info("Connected to Postgresql DB!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (c == null || stmt == null) {
            Bukkit.getLogger().severe("Connection statement INVALID");
        }
    }

    public void closeDefault() {
        if (NRCore.config.SQLENABLED) {
            try {
                c.close();
                stmt.close();
                Bukkit.getLogger().info("Closing database pools");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
