package cn.originmc.plugins.mcborder.papi;

import cn.originmc.plugins.mcborder.BiomeTranslation;
import cn.originmc.plugins.mcborder.McBorder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class BiomePlaceholder extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "McBorder";
    }

    @Override
    public String getAuthor() {
        return "Yeqi";
    }

    @Override
    public String getVersion() {
        return "2.4";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equalsIgnoreCase("Biome")) {
            Biome biome= player.getWorld().getBiome(player.getLocation());
            if (McBorder.getLang().equalsIgnoreCase("chinese")){
                return BiomeTranslation.getTranslation(biome.name());
            }else {
                return biome.name();
            }
        }

        return null;
    }
}
