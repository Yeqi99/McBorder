package cn.originmc.plugins.mcborder.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;


public class PlayerOutBorderEvent extends Event {
    private PlayerMoveEvent origin;
    public PlayerOutBorderEvent(PlayerMoveEvent event) {
        super(true);
        this.origin=event;
    }
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public PlayerMoveEvent getOrigin() {
        return origin;
    }
}
