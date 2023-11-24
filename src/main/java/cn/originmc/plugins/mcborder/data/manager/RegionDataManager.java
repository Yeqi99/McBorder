package cn.originmc.plugins.mcborder.data.manager;

import cn.originmc.plugins.mcborder.data.RegionData;
import cn.originmc.plugins.mcborder.region.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionDataManager {

    public static Region getRegion(String id){
        for (Region region : RegionData.regions) {
            if (region.getId().equalsIgnoreCase(id)){
                return region;
            }
        }
        return null;
    }
    public static Region getRegion(Player player){
        Location location=player.getLocation();
        for (Region region : RegionData.regions) {
            if (region.isInsideRegion(location)){
                return region;
            }
        }
        return getRegion("default");
    }
    public static Region getRegion(Location location){
        for (Region region : RegionData.regions) {
            if (region.isInsideRegion(location)){
                return region;
            }
        }
        return getRegion("default");
    }
}
