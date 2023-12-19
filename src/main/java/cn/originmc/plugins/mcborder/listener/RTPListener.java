package cn.originmc.plugins.mcborder.listener;


import cn.originmc.plugins.mcborder.McBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RTPListener implements Listener {
    private static final Map<UUID, Long> immunePlayers = new HashMap<>();
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) event.getEntity();
            UUID playerId = player.getUniqueId();

            // 检查玩家是否处于免疫状态
            if (immunePlayers.containsKey(playerId)) {
                long immunityStartTime = immunePlayers.get(playerId);
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - immunityStartTime;

                // 如果免疫时间已经超过时间，移除免疫状态
                if (elapsedTime >= McBorder.getInstance().getConfig().getLong("rtp.fall_immunity_time",10000)) {
                    immunePlayers.remove(playerId);
                } else {
                    immunePlayers.remove(playerId);
                    event.setCancelled(true); // 取消该次伤害事件
                }
            }
        }
    }

    // 给予玩家摔落免疫
    public static void giveFallDamageImmunity(Player player) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        immunePlayers.put(playerId, currentTime);
    }
}
