package de.superklug.islandclash.utils.entities;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class Team {
    
    private @Getter @Setter String name;
    private @Getter @Setter String prefix;
    
    private @Getter @Setter Map<Player, Integer> roundKills;
    
    private @Getter @Setter List<Player> members;

    public Team() {
    }

}
