package de.superklug.islandclash.utils.countdowns;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.enums.GameState;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class EndingPhaseCountdown extends BukkitRunnable {
    
    private final IslandClash module;
    
    private @Getter int time = 11;

    public EndingPhaseCountdown(final IslandClash module) {
        this.module = module;
    }

    @Override
    public void run() {
        
        if(!module.getGameState().equals(GameState.ENDING)) {
            this.cancel();
            return;
        }
        time--;
        
        Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
            switch(time) {

                case 10: case 9: case 8: case 7: case 6: case 5: case 4: case 3: case 2: case 1:
                    players.playSound(players.getLocation(), Sound.NOTE_SNARE_DRUM, 1F, (float) time);
                    players.sendMessage(module.format(module.getPrefix() + "Der Server restartet in §b{0} §7Sekunde{1}§8!", time, (time == 1 ? "" : "n")));
                    break;

                case 0:
                    
                    players.kickPlayer(module.getPrefix() + "Der Server restartet nun§8!");
                    
                    break;

                default:
                    break;

            }
        });
        
    }

}
