package de.superklug.islandclash.listeners;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.events.AchievmentReceiveEvent;
import de.superklug.islandclash.utils.entities.Achievment;
import de.superklug.islandclash.utils.entities.User;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AchievmentReceiveListener implements Listener {
    
    private final IslandClash module;

    public AchievmentReceiveListener(final IslandClash module) {
        this.module = module;
    }
    
    @EventHandler
    public void onAchievmentReceiveEvent(final AchievmentReceiveEvent event) {
        Player player = event.getPlayer();
        User user = (User) player.getMetadata("userData").get(0).value();
        Achievment achievment = event.getAchievment();
        
        if(user.getAchievmentsReceived().contains(achievment.getName())) {
            event.setCancelled(true);
        }
        
        if(!module.getGameManager().getAvailibleAchievments().containsKey(achievment.getName())) {
            event.setCancelled(true);
        }
        
        if(!event.isCancelled()) {
            
            final int coins = module.getGameManager().getAvailibleAchievments().get(achievment.getName()).getReward();
            
            user.getAchievmentsReceived().add(achievment.getName());
            user.setCoins(user.getCoins() + coins);
            
            player.spigot().playEffect(player.getLocation(), Effect.CLOUD, 1, 1, 1, 1, 1, 1, 128, 16);
            player.playSound(player.getEyeLocation(), Sound.FIREWORK_LAUNCH, 1, 1);
            player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
            player.sendMessage(module.getPrefix() + " ");
            player.sendMessage(module.getPrefix() + "               §b§nErfolg freigeschaltet§r ");
            player.sendMessage(module.getPrefix() + " ");
            player.sendMessage(module.getPrefix() + "Erfolg §8» §b" + achievment.getDisplayName());
            player.sendMessage(module.getPrefix() + "Belohnung §8» §b" + coins + " §7Coins§8.");
            player.sendMessage(module.getPrefix() + " ");
            player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
            
        }
        
    }

}
