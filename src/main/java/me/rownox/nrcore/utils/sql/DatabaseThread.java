package me.rownox.nrcore.utils.sql;

import lombok.Getter;

public class DatabaseThread extends Thread {
    @Getter
    private boolean online = false;
    @Getter
    private Database DB;

    @Override
    public void run() {
        DB = new Database();
        online = true;
    }
}
