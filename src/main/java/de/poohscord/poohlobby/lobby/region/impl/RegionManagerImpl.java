package de.poohscord.poohlobby.lobby.region.impl;

import de.poohscord.poohlobby.lobby.region.RegionManager;
import de.poohscord.poohlobby.lobby.region.impl.config.RegionLoader;
import de.poohscord.poohlobby.lobby.region.impl.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class RegionManagerImpl implements RegionManager {

    private final List<Region> regions;
    private BukkitTask task;
    private final JavaPlugin plugin;

    public RegionManagerImpl(List<RegionLoader> loaders, JavaPlugin plugin) {
        this.regions = loaders.stream().map(RegionLoader::loadRegions).flatMap(List::stream).toList();
        this.plugin = plugin;
    }

    @Override
    public void run() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> regions.forEach(Region::run), 0, 10);
    }

    @Override
    public void stop() {
        task.cancel();
    }
}
