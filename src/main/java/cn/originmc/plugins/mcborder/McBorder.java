package cn.originmc.plugins.mcborder;

import cn.originmc.plugins.mcborder.command.McBorderCommand;
import cn.originmc.plugins.mcborder.command.McBorderCompleter;
import cn.originmc.plugins.mcborder.data.BorderData;
import cn.originmc.plugins.mcborder.data.LangData;
import cn.originmc.plugins.mcborder.data.RegionData;
import cn.originmc.plugins.mcborder.data.manager.BorderDataManager;
import cn.originmc.plugins.mcborder.data.object.BorderSetting;
import cn.originmc.plugins.mcborder.hook.PlaceholderAPIHook;
import cn.originmc.plugins.mcborder.listener.RTPListener;
import cn.originmc.plugins.mcborder.listener.RegionMoveListener;
import cn.originmc.plugins.mcborder.papi.BiomePlaceholder;
import cn.originmc.plugins.mcborder.papi.BorderPlaceholder;
import cn.originmc.plugins.mcborder.util.register.CommandRegister;
import cn.originmc.plugins.mcborder.util.register.CompleterRegister;
import cn.originmc.plugins.mcborder.util.register.ListenerRegister;
import cn.originmc.plugins.mcborder.util.text.Sender;
import org.bukkit.plugin.java.JavaPlugin;

public final class McBorder extends JavaPlugin {
    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static String getLang() {
        return getInstance().getConfig().getString("lang");
    }

    @Override
    public void onEnable() {
        int pluginId = 19714; // 替换为您的插件ID
        Metrics metrics = new Metrics(this, pluginId);
        instance = this;
        saveDefaultConfig();
        if (getConfig().getBoolean("default_file", true)) {
            saveResource("lang/Chinese.yml", true);
            saveResource("lang/English.yml", true);
            saveResource("border/example.yml", false);
            saveResource("region/default.yml",false);
            saveResource("region/example.yml",false);
        }
        LangData.getData();
        BorderData.getData();
        RegionData.getData();
        PlaceholderAPIHook.hook(getInstance());
        ListenerRegister.register(getInstance(), new RTPListener());
        if (getConfig().getBoolean("region_move_listener")){
            ListenerRegister.register(getInstance(), new RegionMoveListener());
        }
        if (PlaceholderAPIHook.isLoad()) {
            new BiomePlaceholder().register();
            new BorderPlaceholder().register();
        }
        CommandRegister.register(getInstance(), new McBorderCommand(), "McBorder");
        CompleterRegister.register(getInstance(), new McBorderCompleter(), "McBorder");
        new Sender(this).sendToLogger("");
        new Sender(this).sendToLogger("&b     McBorder  v3.0.3-Dependency  Successfully loaded");
        new Sender(this).sendToLogger("&d                    Made by Yeqi");
        new Sender(this).sendToLogger("");
        if (getConfig().getBoolean("on_enable_setting_update", false)) {
            for (BorderSetting borderSetting : BorderDataManager.getBorderSetting()) {
                boolean flag = borderSetting.upWorld();
                if (flag) {
                    new Sender(this).sendToLogger("&aBorder &b" + borderSetting.getWorld() + " &aup successfully");
                } else {
                    new Sender(this).sendToLogger("&cBorder &b" + borderSetting.getWorld() + " &cnot found");
                }
            }
        }

    }

    @Override
    public void onDisable() {
        new Sender(this).sendToLogger("");
        new Sender(this).sendToLogger("&b     McBorder  v3.0.3-Dependency  Successfully unloaded");
        new Sender(this).sendToLogger("&d                    Made by Yeqi");
        new Sender(this).sendToLogger("");
    }
}
