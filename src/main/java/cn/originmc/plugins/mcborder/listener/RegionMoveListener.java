package cn.originmc.plugins.mcborder.listener;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.data.manager.RegionDataManager;
import cn.originmc.plugins.mcborder.region.Region;
import cn.originmc.plugins.mcborder.util.text.Sender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class RegionMoveListener implements Listener {
    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent e){
        Region region= RegionDataManager.getRegion(e.getFrom());
        Region toRegion= RegionDataManager.getRegion(e.getTo());
        if (!toRegion.allowJoin(e.getPlayer())){
            String message=region.denyMessage("deny_join_message");
            new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
            return;
        }
        if (!region.allowMove(e.getPlayer())){
            String message=region.denyMessage("deny_move_message");
            new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
            return;
        }else {
            if (toRegion.getId().equals(region.getId())){
                return;
            }
            if (!region.hasFlag("deny_to")){
                return;
            }
            String denyTo=region.getFlagValue("deny_to");
            if (denyTo.contains("*")){
                String message=region.denyMessage("deny_to_message");
                message=message.replace("!to", toRegion.getDisplay());
                message=message.replace("!from", region.getDisplay());
                new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
                e.setCancelled(true);
                return;
            }
            if (denyTo.contains("|")){
                String[] allowRegions=denyTo.split(",");
                for (String allowRegion : allowRegions) {
                    if (allowRegion.equalsIgnoreCase(toRegion.getId())){
                        String message=region.denyMessage("deny_to_message");
                        message=message.replace("!to", toRegion.getDisplay());
                        message=message.replace("!from", region.getDisplay());
                        new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
                        e.setCancelled(true);
                        return;
                    }
                }
            }else {
                if (toRegion.getId().equalsIgnoreCase(denyTo)){
                    e.setCancelled(true);
                }
            }
        }
    }

}
