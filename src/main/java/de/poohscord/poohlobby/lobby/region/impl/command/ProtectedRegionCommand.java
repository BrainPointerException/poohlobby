package de.poohscord.poohlobby.lobby.region.impl.command;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import de.poohscord.poohlobby.lobby.region.impl.config.ProtectedRegionConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ProtectedRegionCommand implements CommandExecutor {

    private final Map<String, RegionData> regions = new HashMap<>(4);
    private final ProtectedRegionConfig config;

    public ProtectedRegionCommand(ProtectedRegionConfig config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to execute this command!");
            return true;
        }

        if (!player.hasPermission("poohscord.poohlobby.admin")) {
            player.sendMessage("You do not have permission to execute this command!");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("Usage: /protectedregion <regionName> <pos1,pos2,spawn>");
            return false;
        }

        final String regionName = args[0];

        switch (args[1]) {
            case "pos1" -> {
                if (regions.containsKey(regionName)) {
                    final RegionData regionData = regions.get(regionName);
                    regionData.setPos1(player.getLocation().toVector());
                    player.sendMessage("Set pos1 for region " + regionName + " to your current location!");
                } else {
                    regions.put(regionName, new RegionData(null, player.getLocation().toVector(), null));
                    player.sendMessage("Created new region " + regionName + " with pos1 set to your current location!");
                }
            }
            case "pos2" -> {
                if (regions.containsKey(regionName)) {
                    final RegionData regionData = regions.get(regionName);
                    regionData.setPos2(player.getLocation().toVector());
                    player.sendMessage("Set pos2 for region " + regionName + " to your current location!");
                } else {
                    regions.put(regionName, new RegionData(null, null, player.getLocation().toVector()));
                    player.sendMessage("Created new region " + regionName + " with pos2 set to your current location!");
                }
            }
            case "spawn" -> {
                if (regions.containsKey(regionName)) {
                    final RegionData regionData = regions.get(regionName);
                    regionData.setSpawnLocation(player.getLocation());
                    player.sendMessage("Set spawn location for region " + regionName + " to your current location!");
                } else {
                    regions.put(regionName, new RegionData(player.getLocation(), null, null));
                    player.sendMessage("Created new region " + regionName + " with spawn location set to your current location!");
                }
            }
            default -> player.sendMessage("Usage: /protectedregion <regionName> <pos1,pos2,spawn>");
        }

        if (regions.containsKey(regionName) && regions.get(regionName).isComplete()) {
            final RegionData regionData = regions.get(regionName);
            config.createRegion(regionData.getRectangle(), regionName, regionData.spawnLocation);
            player.sendMessage("Region " + regionName + " has been created!");
            regions.remove(regionName);
        }

        return true;
    }

    private static class RegionData {

        private Location spawnLocation;

        private Vector pos1;

        private Vector pos2;

        public RegionData(Location spawnLocation, Vector pos1, Vector pos2) {
            this.spawnLocation = spawnLocation;
            this.pos1 = pos1;
            this.pos2 = pos2;
        }

        public void setPos1(Vector pos1) {
            this.pos1 = pos1;
        }

        public void setPos2(Vector pos2) {
            this.pos2 = pos2;
        }

        public void setSpawnLocation(Location spawnLocation) {
            this.spawnLocation = spawnLocation;
        }

        public boolean isComplete() {
            return spawnLocation != null && pos1 != null && pos2 != null;
        }

        public Rectangle getRectangle() {
            if (pos1 == null || pos2 == null) {
                return null;
            }
            return new Rectangle(pos1, pos2);
        }
    }
}
