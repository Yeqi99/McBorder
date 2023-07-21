package cn.originmc.plugins.mcborder.command;

import cn.originmc.plugins.mcborder.BiomeTranslation;
import cn.originmc.plugins.mcborder.McBorder;
import cn.originmc.plugins.mcborder.data.LangData;
import cn.originmc.plugins.mcborder.listener.RTPEvent;
import cn.originmc.plugins.mcborder.util.command.CommandUtil;
import cn.originmc.plugins.mcborder.util.text.Sender;
import com.google.common.util.concurrent.AbstractScheduledService;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class McBorderCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandUtil cu=new CommandUtil(sender,command,label,args);
        Sender s=new Sender(McBorder.getInstance());
        if (cu.getParameterAmount()==0){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.normal")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-parameters","&c参数不足"));
            return true;
        }
        if (cu.is(0,"help")){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.help")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            s.sendToSender(sender, (List<String>) LangData.getYamlManager().get(McBorder.getLang(),"help"));
            return true;
        }else if (cu.is(0,"setborder")){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.setborder")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount()<3){
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-parameters","&c参数不足"));
            }else {
                World world = Bukkit.getWorld(cu.getParameter(1));
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"world-does-not-exist","&c世界不存在"));
                    return true;
                }
                if (!isNumber(cu.getParameter(2))){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"illegal-parameter","&c非法参数"));
                    return true;
                }
                long sideLen= Long.parseLong(cu.getParameter(2));
                int time=0;
                if (cu.getParameterAmount()==4){
                    time= Integer.parseInt(cu.getParameter(3));
                }
                world.getWorldBorder().setSize(sideLen,time);
            }
            return true;
        }else if (cu.is(0,"setcenter")){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.setcenter")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount()<4){
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-parameters","&c参数不足"));
            }else {
                World world = Bukkit.getWorld(cu.getParameter(1));
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"world-does-not-exist","&c世界不存在"));
                    return true;
                }
                if (!isNumber(cu.getParameter(2))){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"illegal-parameter","&c非法参数"));
                    return true;
                }
                if (!isNumber(cu.getParameter(3))){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"illegal-parameter","&c非法参数"));
                    return true;
                }
                long x= Long.parseLong(cu.getParameter(2));
                long z= Long.parseLong(cu.getParameter(3));
                world.getWorldBorder().setCenter(x,z);
            }
            return true;
        }else if (cu.is(0,"setplayer")){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.setplayer")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount()<2){
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-parameters","&c参数不足"));
            }else {
                Player p = Bukkit.getPlayer(cu.getParameter(1));
                long size=1;
                int time=0;
                if (isNumber(cu.getParameter(2))){
                    size= Long.parseLong(cu.getParameter(2));
                }
                if (cu.getParameterAmount()==4){
                    time= Integer.parseInt(cu.getParameter(3));
                }
                World world = p.getWorld();
                world.getWorldBorder().setCenter(p.getLocation());
                world.getWorldBorder().setSize(size,time);
            }
            return true;
        }else if (cu.is(0,"reborder")){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.reborder")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount()<2){
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-parameters","&c参数不足"));
            } else {
                World world = Bukkit.getWorld(cu.getParameter(1));
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"world-does-not-exist","&c世界不存在"));
                    return true;
                }
                world.getWorldBorder().reset();
                return true;
            }
        } else if (cu.is(0,"replayer")){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.replayer")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount()<2){
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-parameters","&c参数不足"));
            } else {
                Player player=Bukkit.getPlayer(cu.getParameter(1));
                if (player==null){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"player-does-not-exist","&c玩家不存在"));
                    return true;
                }
                World world = player.getWorld();
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"world-does-not-exist","&c世界不存在"));
                    return true;
                }
                world.getWorldBorder().reset();
                return true;
            }
        } else if (cu.is(0,"replayer")){
            if (!cu.isAdmin()){
                if (!cu.hasPerm("McBorder.replayer")){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount()<2){
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"insufficient-parameters","&c参数不足"));
            } else {
                Player player=Bukkit.getPlayer(cu.getParameter(1));
                if (player==null){
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"player-does-not-exist","&c玩家不存在"));
                    return true;
                }
                World world = player.getWorld();
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(),"world-does-not-exist","&c世界不存在"));
                    return true;
                }
                world.getWorldBorder().reset();
                return true;
            }
        }else if (cu.is(0, "getcenter")) {
            if (!cu.isAdmin()) {
                if (!cu.hasPerm("McBorder.getcenter")) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-permissions", "&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount() < 2) {
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-parameters", "&c参数不足"));
            } else {
                World world = Bukkit.getWorld(cu.getParameter(1));
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "world-does-not-exist", "&c世界不存在"));
                    return true;
                }
                Location center = world.getWorldBorder().getCenter();
                s.sendToSender(sender, "世界中心坐标: X=" + center.getX() + ", Z=" + center.getZ());
                return true;
            }
        } else if (cu.is(0, "getsize")) {
            if (!cu.isAdmin()) {
                if (!cu.hasPerm("McBorder.getsize")) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-permissions", "&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount() < 2) {
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-parameters", "&c参数不足"));
            } else {
                World world = Bukkit.getWorld(cu.getParameter(1));
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "world-does-not-exist", "&c世界不存在"));
                    return true;
                }
                double size = world.getWorldBorder().getSize();
                s.sendToSender(sender, "世界边界尺寸: " + size);
                return true;
            }
        }else if(cu.is(0,"reload")){
            if (!cu.isAdmin()) {
                if (!cu.hasPerm("McBorder.reload")) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-permissions", "&c权限不足"));
                    return true;
                }
            }
            McBorder.getInstance().reloadConfig();
            LangData.getData();
            s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "reload-successful", "&a重载成功！"));
            return true;
        }else if (cu.is(0, "setwarning")) {
            if (!cu.isAdmin()) {
                if (!cu.hasPerm("McBorder.setwarning")) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-permissions", "&c权限不足"));
                    return true;
                }
            }
            if (cu.getParameterAmount() < 4) {
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-parameters", "&c参数不足"));
            } else {
                World world = Bukkit.getWorld(cu.getParameter(1));
                if (world == null) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "world-does-not-exist", "&c世界不存在"));
                    return true;
                }
                if (!isNumber(cu.getParameter(2))) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "illegal-parameter", "&c非法参数"));
                    return true;
                }
                if (!isNumber(cu.getParameter(3))) {
                    s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "illegal-parameter", "&c非法参数"));
                    return true;
                }
                double warningDistance = Double.parseDouble(cu.getParameter(2));
                int warningTime = Integer.parseInt(cu.getParameter(3));
                int bufferDistance = 0;
                double damageAmount = 0;
                if (cu.getParameterAmount() >= 5) {
                    if (isNumber(cu.getParameter(4))) {
                        bufferDistance = Integer.parseInt(cu.getParameter(4));
                    }
                }
                if (cu.getParameterAmount() >= 6) {
                    if (isNumber(cu.getParameter(5))) {
                        damageAmount = Double.parseDouble(cu.getParameter(5));
                    }
                }
                setWarning(world, warningDistance, warningTime, bufferDistance, damageAmount);
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "warning-properties-updated", "&a边界警告属性已更新"));
            }
            return true;
        }else  if (cu.is(0, "increase")) {
            if (!cu.isAdmin() && !cu.hasPerm("McBorder.increase")) {
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-permissions", "&c权限不足"));
                return true;
            }

            String worldName = cu.getParameter(1);
            int increaseValue;

            try {
                increaseValue = Integer.parseInt(cu.getParameter(2));
            } catch (NumberFormatException e) {
                // Invalid increase value
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "illegal-parameter", "&c非法参数"));
                return true;
            }

            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                // World does not exist
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "world-does-not-exist", "&c世界不存在"));
                return true;
            }
            int time=0;
            if (cu.getParameterAmount()==4){
                time= Integer.parseInt(cu.getParameter(3));
            }
            WorldBorder worldBorder = world.getWorldBorder();
            double currentSize = worldBorder.getSize();
            double newSize = currentSize + increaseValue;

            worldBorder.setSize(newSize,time);
            s.sendToSender(sender, "世界边界尺寸已增加至 " + newSize);
            return true;
        } else if (cu.is(0, "reduce")) {
            if (!cu.isAdmin() && !cu.hasPerm("McBorder.reduce")) {
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-permissions", "&c权限不足"));
                return true;
            }

            String worldName = cu.getParameter(1);
            int reduceValue;

            try {
                reduceValue = Integer.parseInt(cu.getParameter(2));
            } catch (NumberFormatException e) {
                // Invalid reduce value
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "illegal-parameter", "&c非法参数"));
                return true;
            }

            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                // World does not exist
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "world-does-not-exist", "&c世界不存在"));
                return true;
            }
            int time=0;
            if (cu.getParameterAmount()==4){
                time= Integer.parseInt(cu.getParameter(3));
            }
            WorldBorder worldBorder = world.getWorldBorder();
            double currentSize = worldBorder.getSize();
            double newSize = currentSize - reduceValue;

            worldBorder.setSize(newSize,time);
            s.sendToSender(sender, "世界边界尺寸已减少至 " + newSize);
            return true;
        } else if (cu.is(0, "rtp")){
            if (!cu.isAdmin() && !cu.hasPerm("McBorder.rtp")) {
                s.sendToSender(sender, (String) LangData.getYamlManager().get(McBorder.getLang(), "insufficient-permissions", "&c权限不足"));
                return true;
            }
            String playerName=cu.getParameter(1);
            if (playerName==null){
                return true;
            }
            String worldName=cu.getParameter(2);
            if (worldName==null){
                return true;
            }
            rtp(playerName,worldName);
            return true;
        }

        return true;
    }
    public static boolean isNumber(String inStrNumber) {
        int i = inStrNumber.length();
        do {
            --i;
            if (i < 0) {
                return true;
            }
        } while(Character.isDigit(inStrNumber.charAt(i)));

        return false;
    }

    public static void setWarning(World world, double warningDistance, int warningTime, double bufferDistance, double damageAmount) {
        if (world == null) {
            // 处理世界不存在的情况
            return;
        }
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setWarningDistance((int) warningDistance);
        worldBorder.setWarningTime(warningTime);
        worldBorder.setDamageBuffer(bufferDistance);
        worldBorder.setDamageAmount(damageAmount);
    }
    public boolean rtp(String playerName, String worldName) {
        Player player = Bukkit.getPlayer(playerName);

        // 检查玩家是否在线
        if (player == null) {
            return false;
        }

        World world = Bukkit.getWorld(worldName);

        // 检查世界是否存在
        if (world == null) {
            return false;
        }

        // 获取世界边界信息
        double borderSize = world.getWorldBorder().getSize();
        int maxBorderSize = (int) (borderSize / 2);
        int minBorderSize = -maxBorderSize;

        // 获取世界边界中心坐标
        int centerX = (int) world.getWorldBorder().getCenter().getX();
        int centerZ = (int) world.getWorldBorder().getCenter().getZ();

        // 在以中心点为基准的世界边界范围内随机生成坐标
        int randomX = randInt(centerX + minBorderSize, centerX + maxBorderSize);
        int randomZ = randInt(centerZ + minBorderSize, centerZ + maxBorderSize);
        Bukkit.getRegionScheduler().execute(McBorder.getInstance(),world,randomX,randomZ, () -> {
            Location location=new Location(world, randomX + 0.5, McBorder.getInstance().getConfig().getDouble("rtp.falling_height",384), randomZ + 0.5);
            RTPEvent.giveFallDamageImmunity(player);
            if (isPaper()||isFolia()){
                player.teleportAsync(location,PlayerTeleportEvent.TeleportCause.COMMAND);
            }else {
                player.teleport(location,PlayerTeleportEvent.TeleportCause.COMMAND);
            }
        });
        return true;
    }

    // 辅助方法：生成指定范围内的随机整数
    private int randInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    public static String getVersion() {
        Server server = Bukkit.getServer();
        String version = server.getVersion().toLowerCase();

        // 判断是否为 Paper 环境
        return version;
    }
    public static boolean isPaper(){
        return getVersion().contains("paper");
    }
    public static boolean isFolia(){
        return getVersion().contains("folia");
    }
}
