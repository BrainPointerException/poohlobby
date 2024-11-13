package de.poohscord.poohlobby;

import de.poohscord.poohlobby.lobby.listener.LobbyListener;
import de.poohscord.poohlobby.lobby.region.RegionManager;
import de.poohscord.poohlobby.lobby.region.impl.RegionManagerImpl;
import de.poohscord.poohlobby.lobby.region.impl.command.ProtectedRegionCommand;
import de.poohscord.poohlobby.lobby.region.impl.command.ServerSendRegionCommand;
import de.poohscord.poohlobby.lobby.region.impl.config.ProtectedRegionConfig;
import de.poohscord.poohlobby.lobby.region.impl.config.RegionLoader;
import de.poohscord.poohlobby.lobby.region.impl.config.ServerSendRegionConfig;
import de.poohscord.poohlobby.lobby.region.impl.config.impl.ProtectedRegionYamlConfig;
import de.poohscord.poohlobby.lobby.region.impl.config.impl.RegionLoaderImpl;
import de.poohscord.poohlobby.lobby.region.impl.config.impl.ServerSendRegionYamlConfig;
import de.poohscord.poohlobby.lobby.region.impl.region.impl.ProtectedRegionImpl;
import de.poohscord.poohlobby.lobby.region.impl.region.impl.ServerSendRegion;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PoohLobbyPlugin extends JavaPlugin {

    private RegionManager rm;

    @Override
    public void onEnable() {
        PlayerManager playerManager = InjectionLayer.boot().instance(ServiceRegistry.class).firstProvider(PlayerManager.class);

        ServerSendRegionConfig ssrConfig = new ServerSendRegionYamlConfig(this);
        ProtectedRegionConfig prConfig = new ProtectedRegionYamlConfig(this);

        RegionLoader protectedLoader = new RegionLoaderImpl<>(ProtectedRegionImpl.class,
                new ProtectedRegionYamlConfig(this).getConfig(), this, playerManager);
        RegionLoader serverSendLoader = new RegionLoaderImpl<>(ServerSendRegion.class, ssrConfig.getConfig(), this,
                playerManager);
        this.rm = new RegionManagerImpl(List.of(protectedLoader, serverSendLoader), this);
        this.rm.run();

        getCommand("serversendregion").setExecutor(new ServerSendRegionCommand(ssrConfig, playerManager));
        getCommand("protectedregion").setExecutor(new ProtectedRegionCommand(prConfig));

        LobbyListener lobbyListener = new LobbyListener(protectedLoader.getSpawnRegion());
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(lobbyListener, this);
        lobbyListener.setupLobbyServer();
    }

    @Override
    public void onDisable() {
        this.rm.stop();
    }

}
