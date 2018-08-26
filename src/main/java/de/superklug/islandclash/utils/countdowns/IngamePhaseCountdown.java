package de.superklug.islandclash.utils.countdowns;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import de.superklug.islandclash.utils.enums.GameState;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class IngamePhaseCountdown extends BukkitRunnable {
    
    private final IslandClash module;
    
    private @Getter int time = (60 * 60);

    public IngamePhaseCountdown(final IslandClash module) {
        this.module = module;
    }

    @Override
    public void run() {
        
        if(!module.getGameState().equals(GameState.INGAME)) {
            this.cancel();
            return;
        }
        time--;
        
        Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
            
            module.setRemainingRoundTime(time);
            module.getPlayerScoreboard().update(players);
            
            switch(time) {
                
                case (60 * 45): case (60 * 30): case (60 * 20): case (60 * 15): case (60 * 10):
                case (60 * 5): case (60 * 4): case (60 * 3): case (60 * 2): case 60:
                    players.playSound(players.getLocation(), Sound.NOTE_BASS, 1F, (float) time);
                    players.sendMessage(module.format(module.getPrefix() + "Die Runde endet in §b{0} §7Minute{1}§8!", time, (time == 60 ? "" : "n")));
                    break;
                    
                case 45: case 30: case 20: case 15: case 10: case 5: case 4: case 3: case 2: case 1:
                    players.playSound(players.getLocation(), Sound.NOTE_BASS, 1F, (float) time);
                    players.sendMessage(module.format(module.getPrefix() + "Die Runde endet in §b{0} §7Sekunde{1}§8!", time, (time == 1 ? "" : "n")));
                    break;
                    
                case 0:
                    
                    module.setGameState(GameState.ENDING);
                    module.startEndingPhaseCountdown();
                    
                    module.getGameManager().setGodmodePlayers(true);
                    
                    // Ein Deathmatch??
                    if(!module.getGameManager().getSpectators().contains(players)) {
                        User user = (User) players.getMetadata("userData").get(0).value();
                        
                        user.setUndecidedGames(user.getUndecidedGames() + 1);
                        user.setCoins(user.getCoins() + 100);
                    }
                    
                    module.getGameManager().playWinnerFirework(players);
                    
                    break;
                    
                default:
                    break;
                
            }
        });
        
    }

}
