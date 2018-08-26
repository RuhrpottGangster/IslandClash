package de.superklug.islandclash.listeners;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.events.AchievmentReceiveEvent;
import de.superklug.islandclash.events.KitBuyEvent;
import de.superklug.islandclash.utils.entities.Kit;
import de.superklug.islandclash.utils.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KitBuyListener implements Listener {
    
    private final IslandClash module;

    public KitBuyListener(final IslandClash module) {
        this.module = module;
    }
    
    @EventHandler
    public void onKitBuyEvent(final KitBuyEvent event) {
        Player player = event.getPlayer();
        User user = (User) player.getMetadata("userData").get(0).value();
        Kit kit = event.getKit();
        
        if(user.getKitsBought().contains(kit.getName())) {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1F, 10F);
            player.sendTitle(module.getPrefix(), "§7Du §bbesitzt §7dieses Kit bereits§8!");
            event.setCancelled(true);
        }
        
        if(user.getCoins() < kit.getPrice()) {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1F, 10F);
            player.sendTitle(module.getPrefix(), "§7Dir fehlen §b" + (kit.getPrice() - user.getCoins()) + " §7Coins§8!");
            event.setCancelled(true);
        }
        
        if(!module.getGameManager().getAvailibleKits().containsKey(kit.getName())) {
            event.setCancelled(true);
        }
        
        if(!event.isCancelled()) {
            
            if(user.getKitsBought().isEmpty()) {
                module.runTaskLater(() -> {
                    Bukkit.getServer().getPluginManager().callEvent(new AchievmentReceiveEvent(player, module.getGameManager().getAvailibleAchievments().get("FirstKitBought")));
                }, 20 * 1);
            }
            
            user.setCoins(user.getCoins() - kit.getPrice());
            user.getKitsBought().add(kit.getName());
            
            player.closeInventory();
            module.getGameManager().getActivePlayerKit().put(player, kit);
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 10F);
            player.sendTitle(module.getPrefix(), "§7Kit §b" + kit.getName() + " §7erworben§8!");
            
        }
        
    }

}
