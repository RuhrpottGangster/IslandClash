package de.superklug.islandclash.utils.entities;

import lombok.Getter;
import lombok.Setter;

public class Achievment {
    
    private @Getter @Setter String name;
    private @Getter @Setter String displayName;
    private @Getter @Setter String description;
    private @Getter @Setter int reward;

    public Achievment() {
    }

}
