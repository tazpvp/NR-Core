package me.rownox.rowcore.data;

import javax.xml.stream.Location;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Home {

    private UUID uuid;
    private Map<String, Location> homes;

    public Home(UUID uuid) {
        this.uuid = uuid;
        this.homes = new HashMap<>();
    }

    public void addHome(String name, Location loc) {
        homes.put(name, loc);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map getHomes() {
        return homes;
    }

    public Location getHome(String name) {
        return homes.get(name);
    }
}
