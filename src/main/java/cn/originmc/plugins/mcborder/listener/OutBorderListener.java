package cn.originmc.plugins.mcborder.listener;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.api.event.PlayerOutBorderEvent;
import cn.originmc.plugins.mcborder.data.LangData;
import cn.originmc.plugins.mcborder.hook.PlaceholderAPIHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OutBorderListener implements Listener {
    @EventHandler
    public static void whenPlayerOutBorder(PlayerOutBorderEvent e){
        Player player=e.getOrigin().getPlayer();
        String title= PlaceholderAPIHook.getPlaceholder(player,(String) LangData.getYamlManager()
                .get(McBorder.getLang(), "out-border-title", "&c你走出了边界，可能会收到伤害"));
        String sub= PlaceholderAPIHook.getPlaceholder(player,(String) LangData.getYamlManager()
                .get(McBorder.getLang(), "out-border-sub", "&c边界大小：&d%McBorder_Size%"));
        player.sendTitle(title,sub);
    }
}
