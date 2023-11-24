package cn.originmc.plugins.mcborder.data.manager;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.data.LangData;

public class LangDataManager {
    public static String getText(String key,String defaultValue){
        return  (String) LangData.getYamlManager().get(McBorder.getLang(), key.toLowerCase(), defaultValue);
    }
}
