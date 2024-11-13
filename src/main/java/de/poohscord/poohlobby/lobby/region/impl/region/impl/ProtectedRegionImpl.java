package de.poohscord.poohlobby.lobby.region.impl.region.impl;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import de.poohscord.poohlobby.lobby.region.impl.region.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectedRegionImpl implements ProtectedRegion {

    private final JavaPlugin plugin;

    private final String name;

    private final Rectangle rect;

    private final Location spawnLocation;

    public ProtectedRegionImpl(JavaPlugin plugin, Rectangle rect, String name, Location spawnLocation) {
        this.plugin = plugin;
        this.name = name;
        this.rect = rect;
        this.spawnLocation = spawnLocation;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().stream().filter(player -> !rect.isInside(player.getLocation().toVector()))
                .forEach(player -> Bukkit.getScheduler().runTask(plugin, () -> {
                    if (!player.hasPermission(getByPassPermission())) {
                        player.teleport(spawnLocation);
                    }
                }));
    }

    @Override
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public String getByPassPermission() {
        return "poohscord.poohlobby.protectedregion.bypass";
    }
}
