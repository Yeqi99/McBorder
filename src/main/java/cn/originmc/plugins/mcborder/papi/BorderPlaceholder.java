package cn.originmc.plugins.mcborder.papi;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.data.LangData;
import cn.originmc.plugins.mcborder.data.RegionData;
import cn.originmc.plugins.mcborder.data.manager.LangDataManager;
import cn.originmc.plugins.mcborder.data.manager.RegionDataManager;
import cn.originmc.plugins.mcborder.region.Region;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class BorderPlaceholder extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "BorderInfo"; // 您的插件标识符，用于调用自定义变量
    }

    @Override
    public String getAuthor() {
        return "Yeqi"; // 您的名字或插件作者
    }

    @Override
    public String getVersion() {
        return "3.0"; // 您的插件版本
    }

    // 处理自定义变量的逻辑
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "1";
        }
        if (identifier.equalsIgnoreCase("BorderSize")) {
            return player.getWorld().getWorldBorder().getSize() + "";
        } else if (identifier.equalsIgnoreCase("BorderCenterX")) {
            return player.getWorld().getWorldBorder().getCenter().getBlockX() + "";
        } else if (identifier.equalsIgnoreCase("BorderCenterZ")) {
            return player.getWorld().getWorldBorder().getCenter().getBlockZ() + "";
        } else if (identifier.equalsIgnoreCase("BorderOutDamage")) {
            return player.getWorld().getWorldBorder().getDamageAmount() + "";
        } else if (identifier.toLowerCase().startsWith("region_in_")){
            String regionId=identifier.replace("region_in_","");
            Region region= RegionDataManager.getRegion(regionId);
            if (region==null){
                return "false";
            }
            return region.isInsideRegion(player.getLocation())+"";
        }else if (identifier.toLowerCase().startsWith("region_name")){
            Region region=RegionDataManager.getRegion(player);
            return region.getDisplay();
        }
        return "2";
    }
}
