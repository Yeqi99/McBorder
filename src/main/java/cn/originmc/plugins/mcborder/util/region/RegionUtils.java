package cn.originmc.plugins.mcborder.util.region;

import cn.originmc.plugins.mcborder.region.Region;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RegionUtils {

    public static void sortByWeight(List<Region> regions) {
        Comparator<Region> byWeight = Comparator.comparingDouble(Region::getWeight).reversed();
        regions.sort(byWeight);
    }
}
