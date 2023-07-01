package cn.originmc.plugins.mcborder.data;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.util.data.YamlManager;

public class LangData {
    private static YamlManager yamlManager;
    public static void getData(){
        yamlManager=new YamlManager(McBorder.getInstance(),"lang",true);
    }

    public static YamlManager getYamlManager() {
        return yamlManager;
    }
}
