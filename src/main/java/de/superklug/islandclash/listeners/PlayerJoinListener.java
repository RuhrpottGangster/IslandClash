package de.superklug.islandclash.listeners;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import de.superklug.islandclash.utils.enums.InventoryType;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    
    private final IslandClash module;

    public PlayerJoinListener(final IslandClash module) {
        this.module = module;
    }
    
    @EventHandler
    public void onPlayerJoinEvent(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        player.setFireTicks(0);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
        player.spigot().setCollidesWithEntities(false);
        player.setLevel(0);
        player.setExp(0);
        
        if (player.isDead()) {
            player.spigot().respawn();
        }
        
        player.getActivePotionEffects().forEach((effects) -> {
            player.removePotionEffect(effects.getType());
        });
        
        module.getPlayerScoreboard().set(player);
        
        module.getBackendManager().getUser(player.getUniqueId().toString(), (User user) -> {
            module.getPlayerScoreboard().update(player);
        });
        
        player.getInventory().clear();
        
        switch(module.getGameState()) {
            
            case LOBBY:
                module.getPlayerScoreboard().updatePlayerGroup(player, false);
                
                Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
                    players.showPlayer(player);
                    player.showPlayer(players);
                });

                player.setAllowFlight(false);
                player.setGameMode(GameMode.ADVENTURE);
                
                player.getInventory().setContents(module.getInventoryManager().getInventory(InventoryType.HOTBAR).getContents());
                
                if(module.getLocationManager().getLocation("Lobby_Spawn", 0) != null) {
                    player.teleport(module.getLocationManager().getLocation("Lobby_Spawn", 0));
                }
                
                module.getBackendManager().getTopUser(3, (List<User> t) -> {

                    if (t.get(0).getUuid().equals(player.getUniqueId().toString())) {
                        player.getInventory().setHelmet(module.item(Material.DIAMOND_HELMET).setDisplayname("§b#01 §7im Ranking").build());
                    } else if (t.get(1).getUuid().equals(player.getUniqueId().toString())) {
                        player.getInventory().setHelmet(module.item(Material.GOLD_HELMET).setDisplayname("§b#02 §7im Ranking").build());
                    } else if (t.get(2).getUuid().equals(player.getUniqueId().toString())) {
                        player.getInventory().setHelmet(module.item(Material.IRON_HELMET).setDisplayname("§b#03 §7im Ranking").build());
                    }

                });
                
                int currentlyOnline = Bukkit.getServer().getOnlinePlayers().size();
                int maxPlayers = (module.getGameManager().getMaxTeams() * module.getGameManager().getMaxPlayersATeam());
                
                if(currentlyOnline == (module.getGameManager().getMaxPlayersATeam() + 1)) {
                    module.startLobbyPhaseCountdown();
                }
                
                event.setJoinMessage(module.getPrefix() + player.getDisplayName() + " §7hat das Spiel betreten §8(§b" + currentlyOnline + "§8/§b" + maxPlayers + "§8)");
                break;
            
            case WARMUP: case INGAME: case ENDING:
                module.getGameManager().getSpectators().add(player);
                module.getPlayerScoreboard().updatePlayerGroup(player, true);
                
                Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
                    if (!module.getGameManager().getSpectators().contains(players)) {
                        players.hidePlayer(player);
                    }
                });
                
                player.setAllowFlight(true);
                player.setGameMode(GameMode.CREATIVE);
                
                if(module.getLocationManager().getLocation(module.getMapName(), 1) != null) {
                    player.teleport(module.getLocationManager().getLocation(module.getMapName(), 1));
                }
                
                event.setJoinMessage(null);
                break;
                
            default:
                break;
            
        }
        
    }

}
