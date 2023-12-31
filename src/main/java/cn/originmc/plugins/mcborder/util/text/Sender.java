package cn.originmc.plugins.mcborder.util.text;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static cn.originmc.plugins.mcborder.util.text.Color.toColor;


public class Sender {
    private static JavaPlugin plugin;

    /**
     * 构造方法
     * @param plugin 插件实例
     */
    public Sender(JavaPlugin plugin){
        setPlugin(plugin);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        Sender.plugin = plugin;
    }

    /**
     * 向控制台发送字符串
     * @param inStr 要发送的字符串
     */
    public void sendToLogger(String inStr){
        getPlugin().getServer().getConsoleSender().sendMessage(toColor(inStr));
    }
    /**
     * 向控制台发送字符串列表
     * @param inList 要发送的字符串列表
     */
    public void sendToLogger(List<String> inList){
        for (String s : inList) {
            sendToLogger(s);
        }
    }

    /**
     * 给玩家发送字符串
     * @param player 对应玩家
     * @param inStr 要发送的字符串
     */
    public void sendToPlayer(Player player, String inStr){
        player.sendMessage(toColor(inStr));
    }

    /**
     * 给玩家发送字符串列表
     * @param player 对应玩家
     * @param inList 要发送的字符串列表
     */
    public void sendToPlayer(Player player, List<String> inList){
        for (String s : inList) {
            player.sendMessage(toColor(s));
        }
    }

    /**
     * 给玩家发送交互文本列表
     * @param player 对应玩家
     * @param bcList 交互文本列表
     */
    public void sendToPlayerBC(Player player, List<BaseComponent[]> bcList){
        if(bcList==null){
            return;
        }
        for(BaseComponent[] bc:bcList){
            player.spigot().sendMessage(bc);
        }
    }

    /**
     * 给玩家发送交互文本
     * @param player 对应玩家
     * @param bc 交互文本
     */
    public void sendToPlayerBC(Player player, BaseComponent[] bc){
        if(bc==null){
            return;
        }
        player.spigot().sendMessage(bc);
    }
    public void sendToSender(CommandSender sender, String inStr) {
        sender.sendMessage(toColor(String.valueOf(inStr)));
    }
    public void sendToSender(CommandSender sender, List<String> inList) {
        for (String s : inList) {
            sender.sendMessage(toColor(s));
        }
    }
    public void sendToAllPlayer(String str){
        Bukkit.broadcastMessage(toColor(str));
    }
    public void sendToAllPlayer(List<String> inList){
        for (String s : inList) {
            Bukkit.broadcastMessage(toColor(s));
        }
    }
    public void sendToAllTitle(String title,String sub,int fadeIn,int stay,int fadeOut){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendTitle(toColor(title),toColor(sub),fadeIn,stay,fadeOut);
        }
    }
}
