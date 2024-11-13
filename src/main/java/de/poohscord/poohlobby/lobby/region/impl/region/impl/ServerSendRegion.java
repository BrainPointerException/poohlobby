package de.poohscord.poohlobby.lobby.region.impl.region.impl;

import de.poohscord.poohlobby.lobby.geometry.Rectangle;
import de.poohscord.poohlobby.lobby.region.impl.region.Region;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import eu.cloudnetservice.modules.bridge.player.executor.ServerSelectorType;
import org.bukkit.Bukkit;

public class ServerSendRegion implements Region {

    private final String name, serverName;

    private final Rectangle rect;

    private final PlayerManager playerManager;

    public ServerSendRegion(String name, Rectangle rect, String serverName, PlayerManager playerManager) {
        this.name = name;
        this.rect = rect;
        this.serverName = serverName;
        this.playerManager = playerManager;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().stream().filter(player -> rect.isInside(player.getLocation().toVector()))
                .forEach(player -> playerManager.playerExecutor(player.getUniqueId()).connectToGroup(serverName, ServerSelectorType.RANDOM));
    }
}
