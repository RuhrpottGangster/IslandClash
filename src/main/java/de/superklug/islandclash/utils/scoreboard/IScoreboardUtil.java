package de.superklug.islandclash.utils.scoreboard;

import org.bukkit.entity.Player;

public interface IScoreboardUtil {
    
    void set(final Player player);
    
    void update(final Player player);
    
    void animate();

}
