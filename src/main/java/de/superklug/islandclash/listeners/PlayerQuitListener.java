package de.superklug.islandclash.listeners;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    
    private final IslandClash module;

    public PlayerQuitListener(final IslandClash module) {
        this.module = module;
    }
    
    @EventHandler
    public void onPlayerQuitEvent(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = (User) player.getMetadata("userData").get(0).value();
        
        module.getBackendManager().updateUser(user, (User t) -> {
        });
        
        int currentlyOnline = (Bukkit.getServer().getOnlinePlayers().size() - 1);
        int maxPlayers = (module.getGameManager().getMaxTeams() * module.getGameManager().getMaxPlayersATeam());

        event.setQuitMessage(module.getPrefix() + player.getDisplayName() + " §7hat das Spiel verlassen §8(§b" + currentlyOnline + "§8/§b" + maxPlayers + "§8)");
        
        module.getGameManager().checkWinner();
        
    }

}
