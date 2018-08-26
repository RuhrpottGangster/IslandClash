package de.superklug.islandclash.utils.countdowns;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.enums.GameState;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class WarmupPhaseCountdown extends BukkitRunnable {
    
    private final IslandClash module;
    
    private @Getter int time = 6;

    public WarmupPhaseCountdown(final IslandClash module) {
        this.module = module;
    }

    @Override
    public void run() {
        
        if(!module.getGameState().equals(GameState.WARMUP)) {
            this.cancel();
            return;
        }
        time--;
        
        Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
            switch(time) {
                
                case 5:
                    players.playSound(players.getLocation(), Sound.HORSE_SADDLE, 1F, 5F);
                    players.getInventory().setArmorContents(module.getGameManager().getActivePlayerKit().get(players).getArmorContents());
                    players.getInventory().setContents(module.getGameManager().getActivePlayerKit().get(players).getInventoryContents());
                    break;
                    
                case 4: case 3: case 2: case 1:
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1F, (float) time);
                    players.sendMessage(module.format(module.getPrefix() + "Die Aufwärmphase endet in §b{0} §7Sekunde{1}§8!", time, (time == 1 ? "" : "n")));
                    break;
                    
                case 0:
                    
                    module.setGameState(GameState.INGAME);
                    module.startIngamePhaseCountdown();
                    
                    module.getGameManager().setGodmodePlayers(false);
                    
                    module.getGameManager().setFreezePlayers(false);
                    players.playSound(players.getLocation(), Sound.ENDERDRAGON_WINGS, 1F, 5F);
                    players.sendMessage(module.getPrefix() + "Viel Glück§8, §7du wirst es auch brauchen§8!");
                    break;
                    
                default:
                    break;
                    
            }
        });
        
    }

}
