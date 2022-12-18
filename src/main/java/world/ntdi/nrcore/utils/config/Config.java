package world.ntdi.nrcore.utils.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.nrcore.NRCore;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Objects;

public class Config extends YamlConfiguration {

    private final String filename;
    private final File file;
    private final JavaPlugin plugin;

    /**
     * Creates a new config with the given filename.
     */
    public Config(@Nonnull final String filename, JavaPlugin plugin) {
        this.filename = filename;
        this.plugin = plugin;
        file = new File(NRCore.getInstance().getDataFolder(), filename);
        loadDefaults();
        reload();
    }

    private void loadDefaults() {
        final YamlConfiguration defaultConfig = new YamlConfiguration();

        try (final InputStream inputStream = NRCore.getInstance().getResource(filename)) {
            if (inputStream != null) {
                try (final Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
                    defaultConfig.load(reader);
                }
            }
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Could not load included config file " + filename, exception);
        } catch (final InvalidConfigurationException exception) {
            throw new IllegalArgumentException("Invalid default config for " + filename, exception);
        }

        setDefaults(defaultConfig);
    }

    /**
     * Reloads the configuration
     */
    public void reload() {
        saveDefaultConfig();
        try {
            load(file);
        } catch (final IOException exception) {
            new IllegalArgumentException("Could not find or load file " + filename, exception).printStackTrace();
        } catch (final InvalidConfigurationException exception) {
            Bukkit.getLogger().severe("Your config file " + filename + " is invalid, using default values now. Please fix the below mentioned errors and try again:");
            exception.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new UncheckedIOException(new IOException("Could not create directory " + parent.getAbsolutePath()));

            }
            plugin.saveResource(filename, false);
        }
    }

    /**
     * Saves the configuration under its original file name
     *
     * @throws IOException if the underlying YamlConfiguration throws it
     */
    public void save() throws IOException {
        this.save(file);
    }
}