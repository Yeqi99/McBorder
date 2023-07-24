package cn.originmc.plugins.mcborder.papi;

import cn.originmc.plugins.mcborder.BiomeTranslation;
import cn.originmc.plugins.mcborder.McBorder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class BorderPlaceholder extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "McBorder"; // 您的插件标识符，用于调用自定义变量
    }

    @Override
    public String getAuthor() {
        return "Yeqi"; // 您的名字或插件作者
    }

    @Override
    public String getVersion() {
        return "2.4"; // 您的插件版本
    }

    // 处理自定义变量的逻辑
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equalsIgnoreCase("BorderSize")) {
            return player.getWorld().getWorldBorder().getSize()+"";
        } else if (identifier.equalsIgnoreCase("BorderCenterX")) {
            return player.getWorld().getWorldBorder().getCenter().getBlockX()+"";
        } else if (identifier.equalsIgnoreCase("BorderCenterZ")) {
            return player.getWorld().getWorldBorder().getCenter().getBlockZ()+"";
        } else if (identifier.equalsIgnoreCase("BorderOutDamage")) {
            return player.getWorld().getWorldBorder().getDamageAmount()+"";
        }

        return null;
    }
}
