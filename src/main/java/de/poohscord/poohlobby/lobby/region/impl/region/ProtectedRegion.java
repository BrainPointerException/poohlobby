package de.poohscord.poohlobby.lobby.region.impl.region;

import org.bukkit.Location;

public interface ProtectedRegion extends Region {

    Location getSpawnLocation();

    String getByPassPermission();

}
