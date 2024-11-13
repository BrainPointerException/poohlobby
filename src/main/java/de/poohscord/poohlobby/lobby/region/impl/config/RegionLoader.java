package de.poohscord.poohlobby.lobby.region.impl.config;

import de.poohscord.poohlobby.lobby.region.impl.region.ProtectedRegion;
import de.poohscord.poohlobby.lobby.region.impl.region.Region;

import java.util.List;

public interface RegionLoader {

    List<Region> loadRegions();

    ProtectedRegion getSpawnRegion();

}
