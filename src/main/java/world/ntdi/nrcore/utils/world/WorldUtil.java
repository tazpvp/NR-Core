package world.ntdi.nrcore.utils.world;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

public class WorldUtil {
    public World cloneWorld(String backup, String target) {
        File srcDir = new File(Bukkit.getServer().getWorldContainer(), backup);
        if (!srcDir.exists()) {
            Bukkit.getLogger().warning("World does not exist!");
            return null;
        }
        String dee = target;
        File destDir = new File(Bukkit.getServer().getWorldContainer(), dee);
        try {
            FileUtils.copyDirectory(srcDir, destDir);
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (File file : destDir.listFiles())
                        if (file.isFile())
                            if (file.getName().equalsIgnoreCase("uid.dat"))
                                file.delete();
                }
            }.runTaskAsynchronously(NRCore.getInstance());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Bukkit.getServer().createWorld(new WorldCreator(dee));
        return Bukkit.getServer().getWorld(dee);
    }

    public void deleteWorld(String worldName) {
        World w = Bukkit.getWorld(worldName);
        File srcDir = new File(Bukkit.getServer().getWorldContainer(), worldName);

        Bukkit.unloadWorld(w, false);

        Executors.newCachedThreadPool().submit(() -> {
            try {
                FileUtils.deleteDirectory(srcDir);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        });
    }
}
