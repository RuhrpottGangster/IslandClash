package de.superklug.islandclash.commands;

import com.google.common.collect.Lists;
import de.superklug.islandclash.IslandClash;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FixCommand implements CommandExecutor {
    
    private final IslandClash module;
    
    private final List<Player> cooldown = Lists.newArrayList();

    public FixCommand(final IslandClash module) {
        this.module = module;
    } 

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage(label);
            return true;
        }
        Player player = (Player) sender;
        
        switch(arguments.length) {
            
            case 0:
                
                if (!module.getGameManager().getSpectators().contains(player)) {
                    if (!this.cooldown.contains(player)) {

                        player.teleport(player);

                        Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
                            players.hidePlayer(player);
                            players.showPlayer(player);
                        });

                        this.cooldown.add(player);
                        player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1F, 10F);
                        player.sendTitle("§8❱ §a✔ §8❰", "§7Du wurdest §bgefixt");
                        player.sendMessage(module.getPrefix() + "Du wurdest §bgefixt§8!");

                        module.runTaskLater(() -> {
                            this.cooldown.remove(player);
                        }, 20 * 5);

                    } else {
                        player.sendMessage(module.getPrefix() + "Du darfst diesen Befehl nur alle §b5 §7Sekunden ausführen§8!");
                    }
                } else {
                    player.sendMessage(module.getPrefix() + "Du darfst dich als §bSpectator §7nicht fixen§8!");
                }
                
                break;
                
            default:
                player.sendMessage(module.getUnknownCommand());
                break;
            
        }
        
        return true;
    }

}
