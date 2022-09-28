package me.rownox.rowcore.data;

import java.util.ArrayList;
import java.util.List;

public class HomesContainer {

    private List<Home> homes;

    public HomesContainer() {
        homes = new ArrayList<>();
    }

    public List<Home> getHomes() {
        return homes;
    }

    public void addHome(Home home) {
        homes.add(home);
    }
}
