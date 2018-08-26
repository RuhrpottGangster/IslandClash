package de.superklug.islandclash.commands;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import java.util.List;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommand implements CommandExecutor {
    
    private final IslandClash module;

    public TopCommand(final IslandClash module) {
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
                
                module.getBackendManager().getTopUser(10, (List<User> list) -> {
                    player.playSound(player.getLocation(), Sound.BURP, 1F, 10F);
                    player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
                    
                    for(int i = 0; i < 10; i++) {
                        if(list.get(i) != null) { // IndexOutOfBounds
                            player.sendMessage(module.getPrefix() + "§b" + "#" + (i + 1) + " §8» §7" + list.get(i).getName() + " §8» §b" + list.get(i).getWonGames() + " §7Siege");
                        }
                        if(i == 9) {
                            break;
                        }
                    }
                    
                    player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
                });
                
                break;
                
            default:
                player.sendMessage(module.getUnknownCommand());
                break;
        }
        
        return true;
    }

}
