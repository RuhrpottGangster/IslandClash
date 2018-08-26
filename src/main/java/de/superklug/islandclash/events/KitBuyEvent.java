package de.superklug.islandclash.events;

import de.superklug.islandclash.utils.entities.Kit;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitBuyEvent extends Event implements Cancellable {
    
    private static final HandlerList handlers = new HandlerList();
    
    private final @Getter Player player;
    private final @Getter Kit kit;
    private boolean cancelled = false;

    public KitBuyEvent(final Player player, final Kit kit) {
        this.player = player;
        this.kit = kit;
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
