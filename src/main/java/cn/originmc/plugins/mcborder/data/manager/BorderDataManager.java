package cn.originmc.plugins.mcborder.data.manager;

import cn.originmc.plugins.mcborder.data.BorderData;
import cn.originmc.plugins.mcborder.data.object.BorderSetting;

import java.util.ArrayList;
import java.util.List;

public class BorderDataManager {
    public static List<BorderSetting> getBorderSetting() {
        List<BorderSetting> borderSettings = new ArrayList<>();
        for (String s : BorderData.getYamlManager().getIdList()) {
            BorderSetting borderSetting = new BorderSetting(BorderData.getYamlManager(), s);
            borderSettings.add(borderSetting);
        }
        return borderSettings;
    }

    public static void upAllSetting() {
        for (String s : BorderData.getYamlManager().getIdList()) {
            BorderSetting borderSetting = new BorderSetting(BorderData.getYamlManager(), s);
            borderSetting.upWorld();
        }
    }
}
