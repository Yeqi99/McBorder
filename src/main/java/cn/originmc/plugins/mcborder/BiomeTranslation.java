package cn.originmc.plugins.mcborder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeTranslation {
    private static final Map<String, String> biomeTranslations = new HashMap<>();

    static {
        biomeTranslations.put("OCEAN", "海洋");
        biomeTranslations.put("PLAINS", "平原");
        biomeTranslations.put("DESERT", "沙漠");
        biomeTranslations.put("MOUNTAINS", "山脉");
        biomeTranslations.put("FOREST", "森林");
        biomeTranslations.put("TAIGA", "针叶林");
        biomeTranslations.put("SWAMP", "沼泽");
        biomeTranslations.put("RIVER", "河流");
        biomeTranslations.put("NETHER_WASTES", "下界荒漠");
        biomeTranslations.put("THE_END", "末地");
        biomeTranslations.put("FROZEN_OCEAN", "冻洋");
        biomeTranslations.put("FROZEN_RIVER", "冻河");
        biomeTranslations.put("SNOWY_TUNDRA", "雪原");
        biomeTranslations.put("SNOWY_MOUNTAINS", "雪山");
        biomeTranslations.put("MUSHROOM_FIELDS", "蘑菇岛");
        biomeTranslations.put("MUSHROOM_FIELD_SHORE", "蘑菇岛岸");
        biomeTranslations.put("BEACH", "沙滩");
        biomeTranslations.put("DESERT_HILLS", "沙漠丘陵");
        biomeTranslations.put("WOODED_HILLS", "森林丘陵");
        biomeTranslations.put("TAIGA_HILLS", "针叶林丘陵");
        biomeTranslations.put("MOUNTAIN_EDGE", "山脉边缘");
        biomeTranslations.put("JUNGLE", "丛林");
        biomeTranslations.put("JUNGLE_HILLS", "丛林丘陵");
        biomeTranslations.put("JUNGLE_EDGE", "丛林边缘");
        biomeTranslations.put("DEEP_OCEAN", "深海");
        biomeTranslations.put("STONE_SHORE", "石岸");
        biomeTranslations.put("SNOWY_BEACH", "雪滩");
        biomeTranslations.put("BIRCH_FOREST", "桦木森林");
        biomeTranslations.put("BIRCH_FOREST_HILLS", "桦木森林丘陵");
        biomeTranslations.put("DARK_FOREST", "黑森林");
        biomeTranslations.put("SNOWY_TAIGA", "雪化针叶林");
        biomeTranslations.put("SNOWY_TAIGA_HILLS", "雪化针叶林丘陵");
        biomeTranslations.put("GIANT_TREE_TAIGA", "巨型针叶林");
        biomeTranslations.put("GIANT_TREE_TAIGA_HILLS", "巨型针叶林丘陵");
        biomeTranslations.put("WOODED_MOUNTAINS", "木山");
        biomeTranslations.put("SAVANNA", "热带草原");
        biomeTranslations.put("SAVANNA_PLATEAU", "热带高原");
        biomeTranslations.put("BADLANDS", "恶地");
        biomeTranslations.put("WOODED_BADLANDS_PLATEAU", "木化恶地高原");
        biomeTranslations.put("BADLANDS_PLATEAU", "恶地高原");
        biomeTranslations.put("SMALL_END_ISLANDS", "小型末地岛屿");
        biomeTranslations.put("END_MIDLANDS", "末地中地");
        biomeTranslations.put("END_HIGHLANDS", "末地高地");
        biomeTranslations.put("END_BARRENS", "末地荒漠");
        biomeTranslations.put("WARM_OCEAN", "温暖海洋");
        biomeTranslations.put("LUKEWARM_OCEAN", "温和海洋");
        biomeTranslations.put("COLD_OCEAN", "冷海洋");
        biomeTranslations.put("DEEP_WARM_OCEAN", "深温暖海洋");
        biomeTranslations.put("DEEP_LUKEWARM_OCEAN", "深温和海洋");
        biomeTranslations.put("DEEP_COLD_OCEAN", "深冷海洋");
        biomeTranslations.put("DEEP_FROZEN_OCEAN", "深冻洋");
        biomeTranslations.put("THE_VOID", "虚空");
        biomeTranslations.put("SUNFLOWER_PLAINS", "向日葵平原");
        biomeTranslations.put("DESERT_LAKES", "沙漠湖泊");
        biomeTranslations.put("GRAVELLY_MOUNTAINS", "砂砾山");
        biomeTranslations.put("FLOWER_FOREST", "花海");
        biomeTranslations.put("TAIGA_MOUNTAINS", "针叶林山");
        biomeTranslations.put("SWAMP_HILLS", "沼泽丘陵");
        biomeTranslations.put("ICE_SPIKES", "冰刺");
        biomeTranslations.put("MODIFIED_JUNGLE", "改良丛林");
        biomeTranslations.put("MODIFIED_JUNGLE_EDGE", "改良丛林边缘");
        biomeTranslations.put("TALL_BIRCH_FOREST", "高大桦木森林");
        biomeTranslations.put("TALL_BIRCH_HILLS", "高大桦木森林丘陵");
        biomeTranslations.put("DARK_FOREST_HILLS", "黑森林丘陵");
        biomeTranslations.put("SNOWY_TAIGA_MOUNTAINS", "雪化针叶林山");
        biomeTranslations.put("GIANT_SPRUCE_TAIGA", "巨型云杉林");
        biomeTranslations.put("GIANT_SPRUCE_TAIGA_HILLS", "巨型云杉林丘陵");
        biomeTranslations.put("MODIFIED_GRAVELLY_MOUNTAINS", "改良砂砾山");
        biomeTranslations.put("SHATTERED_SAVANNA", "破碎热带草原");
        biomeTranslations.put("SHATTERED_SAVANNA_PLATEAU", "破碎热带高原");
        biomeTranslations.put("ERODED_BADLANDS", "侵蚀恶地");
        biomeTranslations.put("MODIFIED_WOODED_BADLANDS_PLATEAU", "改良木化恶地高原");
        biomeTranslations.put("MODIFIED_BADLANDS_PLATEAU", "改良恶地高原");
        biomeTranslations.put("BAMBOO_JUNGLE", "竹丛");
        biomeTranslations.put("BAMBOO_JUNGLE_HILLS", "竹丛丘陵");
    }

    public static String getBiomeName(String translatedName) {
        for (Map.Entry<String, String> entry : biomeTranslations.entrySet()) {
            if (entry.getValue().equals(translatedName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getTranslation(String biomeName) {
        return biomeTranslations.getOrDefault(biomeName, biomeName);
    }
    public static List<String> getEnglishBiomeNames() {
        List<String> chineseBiomeNames = new ArrayList<>();
        for (String translatedName : biomeTranslations.keySet()) {
            chineseBiomeNames.add(translatedName);
        }
        return chineseBiomeNames;
    }
    public static List<String> getChineseBiomeNames() {
        List<String> englishBiomeNames = new ArrayList<>();
        for (String translatedName : biomeTranslations.values()) {
            englishBiomeNames.add(translatedName);
        }
        return englishBiomeNames;
    }

}