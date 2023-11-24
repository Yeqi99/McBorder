package cn.originmc.plugins.mcborder.data;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.region.Region;
import cn.originmc.plugins.mcborder.util.data.YamlManager;
import cn.originmc.plugins.mcborder.util.region.RegionUtils;

import java.util.ArrayList;
import java.util.List;

public class RegionData {
    public static List<Region> regions=new ArrayList<>();
    private static YamlManager yamlManager;
    public static void getData(){
        yamlManager=new YamlManager(McBorder.getInstance(),"region",true);
        regions.clear();
        for (String s : yamlManager.getIdList()) {
            Region region=new Region(yamlManager,s);
            regions.add(region);
        }
        RegionUtils.sortByWeight(regions);
    }

    public static YamlManager getYamlManager() {
        return yamlManager;
    }
}
