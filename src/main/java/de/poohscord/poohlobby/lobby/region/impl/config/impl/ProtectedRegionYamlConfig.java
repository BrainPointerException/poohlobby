package de.poohscord.poohlobby.lobby.region.impl.config.impl;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import de.poohscord.poohlobby.lobby.region.impl.config.ProtectedRegionConfig;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ProtectedRegionYamlConfig implements ProtectedRegionConfig {

    private final YamlConfiguration config;
    private final File file;

    public ProtectedRegionYamlConfig(JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "protected-regions.yml");
        if (!file.exists()) {
            plugin.saveResource("protected-regions.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void createRegion(Rectangle rect, String regionName, Location spawnLocation) {
        final String path = "regions." + regionName;
        config.set(path + ".pos1", rect.getPos1());
        config.set(path + ".pos2", rect.getPos2());
        config.set(path + ".spawn", spawnLocation);
        save();
    }

    @Override
    public void removeRegion(String regionName) {
        config.set("regions." + regionName, null);
        save();
    }

    @Override
    public YamlConfiguration getConfig() {
        return config;
    }

    private void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
