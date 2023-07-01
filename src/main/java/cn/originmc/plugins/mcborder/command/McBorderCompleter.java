package cn.originmc.plugins.mcborder.command;


import org.bukkit.Bukkit;
import org.bukkit.World;
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
            completions.add("help");
            completions.add("setborder");
            completions.add("setcenter");
            completions.add("setplayer");
            completions.add("reborder");
            completions.add("replayer");
            completions.add("setwarning");
            completions.add("increase");
            completions.add("reduce");
            completions.add("getcenter");
            completions.add("getsize");
        } else if (args.length >= 2) {
            String subCommand = args[0];

            if (subCommand.equalsIgnoreCase("setborder")) {
                if (args.length == 2) {
                    completions.addAll(getWorldNames());
                } else if (args.length == 3) {
                    completions.add("<sideLen>");
                } else if (args.length == 4) {
                    completions.add("[time]");
                }
            } else if (subCommand.equalsIgnoreCase("setcenter")) {
                if (args.length == 2) {
                    completions.addAll(getWorldNames());
                } else if (args.length == 3) {
                    completions.add("<x>");
                } else if (args.length == 4) {
                    completions.add("<z>");
                }
            } else if (subCommand.equalsIgnoreCase("setplayer")) {
                if (args.length == 2) {
                    completions.addAll(getPlayerNames());
                } else if (args.length == 3) {
                    completions.add("[size]");
                } else if (args.length == 4) {
                    completions.add("[time]");
                }
            } else if (subCommand.equalsIgnoreCase("reborder")) {
                if (args.length == 2) {
                    completions.addAll(getWorldNames());
                }
            } else if (subCommand.equalsIgnoreCase("replayer")) {
                if (args.length == 2) {
                    completions.addAll(getPlayerNames());
                }
            } else if (subCommand.equalsIgnoreCase("setwarning")) {
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
            } else if (subCommand.equalsIgnoreCase("increase") || subCommand.equalsIgnoreCase("reduce")) {
                if (args.length == 2) {
                    completions.addAll(getWorldNames());
                } else if (args.length == 3) {
                    completions.add("<increaseValue>");
                }else if (args.length == 4) {
                    completions.add("[time]");
                }
            } else if (subCommand.equalsIgnoreCase("getsize") || subCommand.equalsIgnoreCase("getcenter")){
                if (args.length == 2) {
                    completions.addAll(getWorldNames());
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
}
