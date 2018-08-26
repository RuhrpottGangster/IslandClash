package de.superklug.islandclash.utils.entities;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class User {
    
    private @Getter @Setter String uuid;
    private @Getter @Setter String name;
    
    private @Getter @Setter int coins;
    
    private @Getter @Setter int kills;
    private @Getter @Setter int deaths;
    
    private @Getter @Setter int wonGames;
    private @Getter @Setter int lostGames;
    private @Getter @Setter int undecidedGames;
    
    private @Getter @Setter int blocksPlaced;
    private @Getter @Setter int blocksBreaked;
    
    private @Getter @Setter int chestsOpend;
    
    private @Getter @Setter List<String> kitsBought;
    private @Getter @Setter List<String> achievmentsReceived;
    
    private @Getter @Setter Map<String, Long> mapRecord;

    public User() {
    }
    
}
