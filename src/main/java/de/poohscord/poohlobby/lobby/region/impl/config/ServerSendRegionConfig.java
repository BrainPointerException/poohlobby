package de.poohscord.poohlobby.lobby.region.impl.config;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import org.bukkit.configuration.file.YamlConfiguration;

public interface ServerSendRegionConfig {

    void createRegion(Rectangle rect, String regionName, String serverName);

    void removeRegion(String regionName);

    YamlConfiguration getConfig();

}
