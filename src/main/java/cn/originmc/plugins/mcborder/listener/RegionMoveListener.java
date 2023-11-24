package cn.originmc.plugins.mcborder.listener;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.data.manager.RegionDataManager;
import cn.originmc.plugins.mcborder.region.Region;
import cn.originmc.plugins.mcborder.util.text.Sender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;


public class RegionMoveListener implements Listener {
    public static Sender sender=new Sender(McBorder.getInstance());
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        Region region= RegionDataManager.getRegion(e.getFrom());
        Region toRegion= RegionDataManager.getRegion(e.getTo());
        if (!toRegion.allowJoin(e.getPlayer())){
            String message=region.denyMessage("deny_join_message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
            return;
        }
        if (!region.allowMove(e.getPlayer())){
            String message=region.denyMessage("deny_move_message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
        }else {
            if (toRegion.getId().equalsIgnoreCase(region.getId())){
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
                sender.sendToPlayer(e.getPlayer(),message);
                e.setCancelled(true);
                return;
            }
            if (denyTo.contains(",")){
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
                    String message=region.denyMessage("deny_to_message");
                    message=message.replace("!to", toRegion.getDisplay());
                    message=message.replace("!from", region.getDisplay());
                    new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Region region= RegionDataManager.getRegion(e.getFrom());
        Region toRegion= RegionDataManager.getRegion(e.getTo());
        if (!toRegion.allowJoin(e.getPlayer())){
            String message=region.denyMessage("deny_join_message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
            return;
        }
        if (!region.allowMove(e.getPlayer())){
            String message=region.denyMessage("deny_move_message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
        }else {
            if (toRegion.getId().equalsIgnoreCase(region.getId())){
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
                sender.sendToPlayer(e.getPlayer(),message);
                e.setCancelled(true);
                return;
            }
            if (denyTo.contains(",")){
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
                    String message=region.denyMessage("deny_to_message");
                    message=message.replace("!to", toRegion.getDisplay());
                    message=message.replace("!from", region.getDisplay());
                    new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
                    e.setCancelled(true);
                }
            }
        }
    }

}
