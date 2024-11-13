package de.poohscord.poohlobby.lobby.region.impl.config;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public interface ProtectedRegionConfig {

    void createRegion(Rectangle rect, String regionName, Location spawnLocation);

    void removeRegion(String regionName);

    YamlConfiguration getConfig();

}
