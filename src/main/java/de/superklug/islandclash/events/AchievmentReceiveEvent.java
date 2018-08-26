package de.superklug.islandclash.events;

import de.superklug.islandclash.utils.entities.Achievment;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AchievmentReceiveEvent extends Event implements Cancellable {
    
    private static final HandlerList handlers = new HandlerList();

    private final @Getter Player player;
    private final @Getter Achievment achievment;
    private boolean cancelled = false;

    public AchievmentReceiveEvent(final Player player, final Achievment achievment) {
        this.player = player;
        this.achievment = achievment;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
