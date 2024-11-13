package de.poohscord.poohlobby.lobby.region.impl.command;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import de.poohscord.poohlobby.lobby.region.impl.config.ServerSendRegionConfig;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ServerSendRegionCommand implements CommandExecutor {

    private final Map<String, RegionData> regions = new HashMap<>(4);
    private final ServerSendRegionConfig config;
    private final PlayerManager playerManager;

    public ServerSendRegionCommand(ServerSendRegionConfig config, PlayerManager playerManager) {
        this.config = config;
        this.playerManager = playerManager;
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
            player.sendMessage("Usage: /serversendregion <regionName> <serverName,pos1,pos2>");
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
            default -> {
                final String serverName = args[1];
                if (regions.containsKey(regionName)) {
                    final RegionData regionData = regions.get(regionName);
                    regionData.setServerName(serverName);
                    player.sendMessage("Set serverName for region " + regionName + " to " + serverName + "!");
                } else {
                    regions.put(regionName, new RegionData(serverName, null, null));
                    player.sendMessage("Created new region " + regionName + " with serverName set to " + serverName + "!");
                }
            }
        }

        if (regions.containsKey(regionName) && regions.get(regionName).isComplete()) {
            final RegionData regionData = regions.get(regionName);
            final Rectangle rect = regionData.getRectangle();

            this.config.createRegion(rect, regionName, regionData.getServerName());

            player.sendMessage("Created region " + regionName + " with pos1 " + rect.getPos1() + " and pos2 "
                    + rect.getPos2() + " and serverName " + regionData.getServerName());
            regions.remove(regionName);
        }

        return true;
    }

    private static class RegionData {

        private String serverName;

        private Vector pos1;

        private Vector pos2;

        public RegionData(String serverName, Vector pos1, Vector pos2) {
            this.serverName = serverName;
            this.pos1 = pos1;
            this.pos2 = pos2;
        }

        public String getServerName() {
            return serverName;
        }

        public void setPos1(Vector pos1) {
            this.pos1 = pos1;
        }

        public void setPos2(Vector pos2) {
            this.pos2 = pos2;
        }

        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        public boolean isComplete() {
            return serverName != null && pos1 != null && pos2 != null;
        }

        public Rectangle getRectangle() {
            if (pos1 == null || pos2 == null) {
                return null;
            }
            return new Rectangle(pos1, pos2);
        }
    }
}
