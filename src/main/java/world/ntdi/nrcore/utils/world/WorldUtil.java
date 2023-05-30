package world.ntdi.nrcore.utils.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WorldUtil {
    public static void cloneWorld(String sourceName, String destinationName) {
        World sourceWorld = Bukkit.getWorld(sourceName);
        if (sourceWorld == null) {
            throw new IllegalArgumentException("Source world not found.");
        }

        if (Bukkit.getWorld(destinationName) != null) {
            throw new IllegalArgumentException("Destination world already exists. Please choose a different name.");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                File sourceFolder = sourceWorld.getWorldFolder();
                File destinationFolder = new File(Bukkit.getWorldContainer(), destinationName);

                if (sourceFolder.exists() && sourceFolder.isDirectory()) {
                    try {
                        Bukkit.getServer().getWorldContainer().mkdirs();
                        copyFolder(sourceFolder, destinationFolder);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to clone the world: " + e.getMessage());
                    }
                } else {
                    throw new RuntimeException("Failed to clone the world. Source world folder not found.");
                }
            }
        }.runTaskAsynchronously(NRCore.getInstance());
    }

    public static void deleteWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalArgumentException("World not found.");
        }

        if (world != Bukkit.getWorlds().get(0)) {
            Bukkit.unloadWorld(world, false);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                File worldFolder = world.getWorldFolder();
                deleteFolder(worldFolder);
            }
        }.runTaskAsynchronously(NRCore.getInstance());
    }

    private static void copyFolder(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String[] files = source.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(destination, file);
                    copyFolder(srcFile, destFile);
                }
            }
        } else {
            Files.copy(source.toPath(), destination.toPath());
        }
    }

    private static void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        folder.delete();
    }
}
