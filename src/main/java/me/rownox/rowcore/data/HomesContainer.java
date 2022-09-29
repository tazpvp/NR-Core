package me.rownox.rowcore.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomesContainer {

    public List<Home> homes;

    public HomesContainer() {
        homes = new ArrayList<>();
    }

    public List<Home> getHomes() {
        return homes;
    }

    public void addHome(Home home) {
        homes.add(home);
    }
    public void removeHome(Home home) { homes.remove(home); }
    public Home getHome(UUID uuid) {
        for (Home home : homes) {
            if (home.getUuid() == uuid) {
                return home;
            }
        }
        return null;
    }
}
