package cn.originmc.plugins.mcborder.listener;


import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.api.event.PlayerOutBorderEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.scheduler.BukkitRunnable;

public class BorderListener {
    @EventHandler
    public static void listener(long time){
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (!onlinePlayer.getWorld().getWorldBorder().isInside(onlinePlayer.getLocation())){
                            Bukkit.getPluginManager().callEvent(new PlayerOutBorderEvent(onlinePlayer));
                        }
                    }
                }
            }.runTaskTimerAsynchronously(McBorder.getInstance(),0,time);
    }
}
