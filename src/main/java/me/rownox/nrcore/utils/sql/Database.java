package me.rownox.nrcore.utils.sql;

import lombok.Getter;
import me.rownox.nrcore.utils.ConfigUtils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    @Getter
    private Connection c = null;
    private Statement stmt = null;

    public Database() {
        if (ConfigUtils.SQLENABLED) {
            Bukkit.getLogger().info("Attempting to connect to SQL Database");
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection(
                        "jdbc:postgresql://" + ConfigUtils.SQLURL + "/postgres",
                        ConfigUtils.SQLUSER, ConfigUtils.SQLPASSWORD
                );
                stmt = c.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void closeDefault() {
        if (ConfigUtils.SQLENABLED) {
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
