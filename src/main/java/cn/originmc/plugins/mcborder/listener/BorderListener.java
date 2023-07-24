package cn.originmc.plugins.mcborder.listener;


import cn.originmc.plugins.mcborder.api.event.PlayerOutBorderEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BorderListener implements Listener {
    @EventHandler
    public static void whenPlayerOutBorder(PlayerMoveEvent e){
        if(e.getPlayer().getWorldBorder().isInside(e.getTo())){
            Bukkit.getPluginManager().callEvent(new PlayerOutBorderEvent(e));
        }
    }
}
