package de.poohscord.poohlobby.lobby.region.impl.config.impl;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import de.poohscord.poohlobby.lobby.region.impl.config.ServerSendRegionConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ServerSendRegionYamlConfig implements ServerSendRegionConfig {

    private final YamlConfiguration config;
    private final File file;

    public ServerSendRegionYamlConfig(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "server-send-regions.yml");
        if (!file.exists()) {
            plugin.saveResource("server-send-regions.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void createRegion(Rectangle rect, String regionName, String serverName) {
        final String path = "regions." + regionName;
        config.set(path + ".pos1", rect.getPos1());
        config.set(path + ".pos2", rect.getPos2());
        config.set(path + ".serverName", serverName);
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
