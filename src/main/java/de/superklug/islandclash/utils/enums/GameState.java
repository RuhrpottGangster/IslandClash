package de.superklug.islandclash.utils.enums;

import lombok.Getter;

public enum GameState {
    
    LOBBY("Lobbyphase"), WARMUP("Aufwärmphase"), INGAME("Spielphase"), ENDING("Endphase");
    
    private final @Getter String name;
    
    GameState(final String name) {
        this.name = name;
    }
    
}
