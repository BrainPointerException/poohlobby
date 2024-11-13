package de.poohscord.poohlobby.lobby.region.impl.config.impl;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import de.poohscord.poohlobby.lobby.region.impl.config.RegionLoader;
import de.poohscord.poohlobby.lobby.region.impl.region.ProtectedRegion;
import de.poohscord.poohlobby.lobby.region.impl.region.Region;
import de.poohscord.poohlobby.lobby.region.impl.region.impl.ProtectedRegionImpl;
import de.poohscord.poohlobby.lobby.region.impl.region.impl.ServerSendRegion;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class RegionLoaderImpl<T extends Region> implements RegionLoader {

    private final JavaPlugin plugin;
    private final YamlConfiguration config;
    private final Class<T> t;
    private ProtectedRegion spawnRegion;
    private final PlayerManager playerManager;

    public RegionLoaderImpl(Class<T> type, YamlConfiguration config, JavaPlugin plugin, PlayerManager playerManager) {
        this.t = type;
        this.config = config;
        this.plugin = plugin;
        this.spawnRegion = null;
        this.playerManager = playerManager;

    }

    @Override
    public List<Region> loadRegions() {
        if (this.config.getConfigurationSection("regions") == null) {
            return List.of();
        }
        return this.config.getConfigurationSection("regions").getKeys(false).stream()
                .map(regionName -> {
                    final String path = "regions." + regionName;
                    final Rectangle rect = new Rectangle(config.getVector(path + ".pos1"),
                            config.getVector(path + ".pos2"));
                    if (t.equals(ProtectedRegionImpl.class)) {
                        this.spawnRegion = new ProtectedRegionImpl(plugin, rect, regionName, config.getLocation(path + ".spawn"));
                        return this.spawnRegion;
                    } else {
                        return new ServerSendRegion(regionName, rect, config.getString(path + ".serverName"), playerManager);
                    }
                }).toList();
    }

    @Override
    public ProtectedRegion getSpawnRegion() {
        return this.spawnRegion;
    }
}
