package cn.originmc.plugins.mcborder.listener;

import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.command.McBorderCommand;
import cn.originmc.plugins.mcborder.data.RegionData;
import cn.originmc.plugins.mcborder.data.manager.LangDataManager;
import cn.originmc.plugins.mcborder.data.manager.RegionDataManager;
import cn.originmc.plugins.mcborder.region.Node;
import cn.originmc.plugins.mcborder.region.Region;
import cn.originmc.plugins.mcborder.util.text.Sender;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RegionMoveListener implements Listener {
    public static Sender sender=new Sender(McBorder.getInstance());
    public static List<String> editors=new ArrayList<>();
    public static Map<String,Region> editRegion=new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        if (!editors.contains(e.getPlayer().getName())){
            return;
        }
        if(!editRegion.containsKey(e.getPlayer().getName())){
            Region region  = new Region();
            region.setId("editRegion");
            region.setDisplay("editRegion");
            region.setWorld("world");
            region.setWeight(1);
            editRegion.put(e.getPlayer().getName(),region);
        }
        Region region=editRegion.get(e.getPlayer().getName());
        Player player=e.getPlayer();
        if (e.getMessage().toLowerCase().contains("add")){
            Node node=new Node(player.getX(),player.getZ());
            region.addNode(node);
            sender.sendToPlayer(e.getPlayer(),
                    LangDataManager
                            .getText("edit-success-add",
                                    "Successfully added a node!(!x,!z)")
                            .replace("!x",node.getX()+"")
                            .replace("!z",node.getZ()+""));

        }else if(e.getMessage().toLowerCase().contains("save")){
            region.saveToFile(RegionData.getYamlManager());
        }else if(e.getMessage().toLowerCase().contains("clear")){
            editRegion.remove(player.getName());
        }else if (e.getMessage().toLowerCase().contains("exit")){
            editors.remove(player.getName());
        }else if(e.getMessage().toLowerCase().contains("region")){
            String[] args=e.getMessage().split(" ");
            Region r= RegionDataManager.getRegion(args[1]);
            if (r==null){
                region.setId("editRegion");
                region.setDisplay("editRegion");
                region.setWorld("world");
                region.setWeight(1);
            }
            region=r;
            editRegion.put(e.getPlayer().getName(),region);
        }else if(e.getMessage().toLowerCase().contains("display")){
            String[] args=e.getMessage().split(" ");
            region.setDisplay(args[1]);
        }else if(e.getMessage().toLowerCase().contains("weight")){
            String[] args=e.getMessage().split(" ");
            region.setWeight(Integer.parseInt(args[1]));
        }else if(e.getMessage().toLowerCase().contains("id")){
            String[] args=e.getMessage().split(" ");
            region.setId(args[1]);
        }else if(e.getMessage().toLowerCase().contains("world")){
            String[] args=e.getMessage().split(" ");
            region.setWorld(args[1]);
        }else if(e.getMessage().toLowerCase().contains("tp")){
            String[] args=e.getMessage().split(" ");
            if (args.length<2){
                e.setCancelled(true);
                return;
            }
            int index= Integer.parseInt(args[1]);
            if (index<0 || index>=region.nodes.size()){
                e.setCancelled(true);
                return;
            }
            Node node= region.nodes.get(index);
            Location location=node.getLocation(player.getLocation());

            if (McBorderCommand.isPaper() || McBorderCommand.isFolia()) {
                player.teleportAsync(location, PlayerTeleportEvent.TeleportCause.COMMAND);
            } else {
                player.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
            }
        }else if (e.getMessage().toLowerCase().contains("look")){
            sender.sendToPlayer(e.getPlayer(),region.getId()+":"+region.getDisplay());
            sender.sendToPlayer(e.getPlayer(),"world:"+region.getWorld());
            sender.sendToPlayer(e.getPlayer(),"weight:"+region.getWeight());
            sender.sendToPlayer(e.getPlayer(),"nodes:");
            for(int i = 0 ; i<region.nodes.size();i++){
                Node node=region.nodes.get(i);
                sender.sendToPlayer(e.getPlayer(),
                        LangDataManager
                                .getText("node-display",
                                        "node.!index:(!x,!z)")
                                .replace("!index",i+"")
                                .replace("!x",node.getX()+"")
                                .replace("!z",node.getZ()+""));
            }
            sender.sendToPlayer(e.getPlayer(),"flags:");
            for (Map.Entry<String, String> entry : region.getFlags().entrySet()) {
                sender.sendToPlayer(e.getPlayer(),
                        LangDataManager
                                .getText("flag-display",
                                        "flag- !key:!value")
                                .replace("!name",entry.getKey())
                                .replace("!value",entry.getValue()));
            }
        }else if(e.getMessage().toLowerCase().contains("insert")){
            String[] args=e.getMessage().split(" ");
            int index= Integer.parseInt(args[1]);
            if (index<0 || index>region.nodes.size()){
                e.setCancelled(true);
                return;
            }
            Node node=new Node(player.getX(),player.getZ());
            region.nodes.add(index,node);
        }else if(e.getMessage().toLowerCase().contains("flag")){
            String[] args=e.getMessage().split(" ");
            String key=args[1];
            String value=args[2];
            region.getFlags().put(key,value);
        }else {
            sender.sendToPlayer(player,"usage:add/save/exit/tp <index>/look/region <id>");
            sender.sendToPlayer(player,"usage:id <id>/display <display>/weight <weight>/world <world>");
            sender.sendToPlayer(player,"usage:insert <index>/flag <key> <value>");
        }
        e.setCancelled(true);
    }



    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        Region region= RegionDataManager.getRegion(e.getFrom());
        Region toRegion= RegionDataManager.getRegion(e.getTo());
        if (!toRegion.allowTeleportJoin(e.getPlayer())){
            String message=region.denyMessage("deny-tp-join-message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
            return;
        }
        if (!region.allowTeleportMove(e.getPlayer())){
            String message=region.denyMessage("deny-tp-move-message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
        }else {
            if (toRegion.getId().equalsIgnoreCase(region.getId())){
                return;
            }
            if (!region.hasFlag("deny-tp-to")){
                return;
            }
            String denyTo=region.getFlagValue("deny-tp-to");
            if (denyTo.contains("*")){
                String message=region.denyMessage("deny-tp-to-message");
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
                        String message=region.denyMessage("deny-tp-to-message");
                        message=message.replace("!to", toRegion.getDisplay());
                        message=message.replace("!from", region.getDisplay());
                        new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
                        e.setCancelled(true);
                        return;
                    }
                }
            }else {
                if (toRegion.getId().equalsIgnoreCase(denyTo)){
                    String message=region.denyMessage("deny-tp-to-message");
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
            String message=region.denyMessage("deny-join-message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
            return;
        }
        if (!region.allowMove(e.getPlayer())){
            String message=region.denyMessage("deny-move-message");
            sender.sendToPlayer(e.getPlayer(),message);
            e.setCancelled(true);
        }else {
            if (toRegion.getId().equalsIgnoreCase(region.getId())){
                return;
            }
            if (!region.hasFlag("deny-to")){
                return;
            }
            String denyTo=region.getFlagValue("deny-to");
            if (denyTo.contains("*")){
                String message=region.denyMessage("deny-to-message");
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
                        String message=region.denyMessage("deny-to-message");
                        message=message.replace("!to", toRegion.getDisplay());
                        message=message.replace("!from", region.getDisplay());
                        new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
                        e.setCancelled(true);
                        return;
                    }
                }
            }else {
                if (toRegion.getId().equalsIgnoreCase(denyTo)){
                    String message=region.denyMessage("deny-to-message");
                    message=message.replace("!to", toRegion.getDisplay());
                    message=message.replace("!from", region.getDisplay());
                    new Sender(McBorder.getInstance()).sendToPlayer(e.getPlayer(),message);
                    e.setCancelled(true);
                }
            }
        }
    }

}
