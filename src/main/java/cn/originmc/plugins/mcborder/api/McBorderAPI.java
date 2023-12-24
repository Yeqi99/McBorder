package cn.originmc.plugins.mcborder.api;

import cn.originmc.plugins.mcborder.command.McBorderCommand;

public class McBorderAPI {
    public static void rtp(String playerName, String worldName) {
        McBorderCommand.rtp(playerName, worldName);
    }

}
