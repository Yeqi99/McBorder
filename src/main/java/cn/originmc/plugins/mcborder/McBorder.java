package cn.originmc.plugins.mcborder;

import cn.originmc.plugins.mcborder.command.McBorderCommand;
import cn.originmc.plugins.mcborder.command.McBorderCompleter;
import cn.originmc.plugins.mcborder.data.LangData;
import cn.originmc.plugins.mcborder.hook.PlaceholderAPIHook;
import cn.originmc.plugins.mcborder.listener.RTPEvent;
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
    public static String getLang(){
        return getInstance().getConfig().getString("lang");
    }
    @Override
    public void onEnable() {
        instance=this;
        saveDefaultConfig();
        saveResource("lang/Chinese.yml",true);
        saveResource("lang/English.yml",true);
        LangData.getData();
        PlaceholderAPIHook.hook(getInstance());
        ListenerRegister.register(getInstance(),new RTPEvent());
        CommandRegister.register(getInstance(),new McBorderCommand(),"McBorder");
        CompleterRegister.register(getInstance(),new McBorderCompleter(),"McBorder");
        new BiomePlaceholder().register();
        new BorderPlaceholder().register();
        new Sender(this).sendToLogger("");
        new Sender(this).sendToLogger("&b     McBorder  v2.4-Dependency  Successfully loaded");
        new Sender(this).sendToLogger("&d                    Made by Yeqi");
        new Sender(this).sendToLogger("");
    }

    @Override
    public void onDisable() {
        new Sender(this).sendToLogger("");
        new Sender(this).sendToLogger("&b     McBorder  v2.4-Dependency  Successfully unloaded");
        new Sender(this).sendToLogger("&d                    Made by Yeqi");
        new Sender(this).sendToLogger("");
    }
}
