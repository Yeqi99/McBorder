package cn.originmc.plugins.mcborder.command;


import cn.originmc.plugins.mcborder.BiomeTranslation;
import cn.originmc.plugins.mcborder.McBorder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class McBorderCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            // 检查是否有help权限，只有有help权限的玩家才能看到help相关补全
            if (sender.hasPermission("McBorder.help")) {
                completions.add("help");
            }
            if (sender.hasPermission("McBorder.setborder")) completions.add("setborder");
            if (sender.hasPermission("McBorder.setcenter")) completions.add("setcenter");
            if (sender.hasPermission("McBorder.setplayer")) completions.add("setplayer");
            if (sender.hasPermission("McBorder.setplayerworld")) completions.add("setplayerworld");
            if (sender.hasPermission("McBorder.reborder")) completions.add("reborder");
            if (sender.hasPermission("McBorder.replayer")) completions.add("replayer");
            if (sender.hasPermission("McBorder.setwarning")) completions.add("setwarning");
            if (sender.hasPermission("McBorder.increase")) completions.add("increase");
            if (sender.hasPermission("McBorder.reduce")) completions.add("reduce");
            if (sender.hasPermission("McBorder.getcenter")) completions.add("getcenter");
            if (sender.hasPermission("McBorder.getsize")) completions.add("getsize");
            if (sender.hasPermission("McBorder.rtp")) completions.add("rtp");
            if (sender.hasPermission("McBorder.reload")) completions.add("reload");
        } else if (args.length >= 2) {
            // 同样，检查每个子命令的权限，只有玩家拥有对应的权限才能看到相关补全
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("setborder")) {
                if (sender.hasPermission("McBorder.setborder")) {
                    if (args.length == 2) {
                        completions.addAll(getWorldNames());
                    } else if (args.length == 3) {
                        completions.add("<sideLen>");
                    } else if (args.length == 4) {
                        completions.add("[time]");
                    }
                }
            } else if (subCommand.equalsIgnoreCase("setcenter")) {
                if (sender.hasPermission("McBorder.setcenter")) {
                    if (args.length == 2) {
                        completions.addAll(getWorldNames());
                    } else if (args.length == 3) {
                        completions.add("<x>");
                    } else if (args.length == 4) {
                        completions.add("<z>");
                    }
                }
            } else if (subCommand.equalsIgnoreCase("setplayer")) {
                if (sender.hasPermission("McBorder.setplayer")) {
                    if (args.length == 2) {
                        completions.addAll(getPlayerNames());
                    } else if (args.length == 3) {
                        completions.add("[size]");
                    } else if (args.length == 4) {
                        completions.add("[time]");
                    }
                }
            }else if (subCommand.equalsIgnoreCase("setplayerworld")) {
                if (sender.hasPermission("McBorder.setplayerworld")) {
                    if (args.length == 2) {
                        completions.addAll(getPlayerNames());
                    } else if (args.length == 3) {
                        completions.add("[size]");
                    } else if(args.length == 4){
                        completions.add("[world]");
                    }else if (args.length == 5) {
                        completions.add("[time]");
                    }
                }
            } else if (subCommand.equalsIgnoreCase("reborder")) {
                if (sender.hasPermission("McBorder.reborder")) {
                    if (args.length == 2) {
                        completions.addAll(getWorldNames());
                    }
                }
            } else if (subCommand.equalsIgnoreCase("replayer")) {
                if (sender.hasPermission("McBorder.replayer")) {
                    if (args.length == 2) {
                        completions.addAll(getPlayerNames());
                    }
                }
            } else if (subCommand.equalsIgnoreCase("setwarning")) {
                if (sender.hasPermission("McBorder.setwarning")) {
                    if (args.length == 2) {
                        completions.addAll(getWorldNames());
                    } else if (args.length == 3) {
                        completions.add("<warningDistance>");
                    } else if (args.length == 4) {
                        completions.add("<warningTime>");
                    } else if (args.length == 5) {
                        completions.add("<bufferDistance>");
                    } else if (args.length == 6) {
                        completions.add("<damageAmount>");
                    }
                }
            } else if (subCommand.equalsIgnoreCase("increase") || subCommand.equalsIgnoreCase("reduce")) {
                if (sender.hasPermission("McBorder.increase") || sender.hasPermission("McBorder.reduce")) {
                    if (args.length == 2) {
                        completions.addAll(getWorldNames());
                    } else if (args.length == 3) {
                        completions.add("<increaseValue>");
                    } else if (args.length == 4) {
                        completions.add("[time]");
                    }
                }
            } else if (subCommand.equalsIgnoreCase("getsize") || subCommand.equalsIgnoreCase("getcenter")) {
                if (sender.hasPermission("McBorder.getsize") || sender.hasPermission("McBorder.getcenter")) {
                    if (args.length == 2) {
                        completions.addAll(getWorldNames());
                    }
                }
            } else if (subCommand.equalsIgnoreCase("rtp")) {
                if (sender.hasPermission("McBorder.rtp")) {
                    if (args.length == 2) {
                        completions.addAll(getPlayerNames());
                    } else if (args.length == 3) {
                        completions.addAll(getWorldNames());
                    }
                }
            }
        }

        // Filter completions based on the current argument
        String currentArg = args[args.length - 1];
        completions.removeIf(completion -> !completion.startsWith(currentArg));

        return completions;
    }
    private List<String> getWorldNames() {
        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            worldNames.add(world.getName());
        }
        return worldNames;
    }

    private List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }
    private List<String> getBiomes(){
        List<String> biomeNames=new ArrayList<>();
        for (Biome value : Biome.values()) {
            biomeNames.add(value.name());
        }
        return biomeNames;
    }

}
