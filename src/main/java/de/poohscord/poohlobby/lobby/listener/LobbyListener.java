package de.poohscord.poohlobby.lobby.listener;

import de.poohscord.poohlobby.lobby.region.impl.region.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyListener implements Listener {

    private final ProtectedRegion spawnRegion;

    public LobbyListener(ProtectedRegion spawnRegion) {
        this.spawnRegion = spawnRegion;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        final Player player = e.getPlayer();
        player.teleport(spawnRegion.getSpawnLocation());
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        if (!player.hasPermission(spawnRegion.getByPassPermission())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        if (!player.hasPermission(spawnRegion.getByPassPermission())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    public void setupLobbyServer() {
        Bukkit.getServer().getWorlds().forEach(w -> {
            w.setTime(0L);
            w.setStorm(false);
            w.setClearWeatherDuration(Integer.MAX_VALUE);
            w.setDifficulty(Difficulty.PEACEFUL);
            w.setGameRuleValue("doDaylightCycle", "false");
            w.setGameRuleValue("doMobSpawning", "false");
        });
    }

}
