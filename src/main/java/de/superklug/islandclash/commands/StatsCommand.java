package de.superklug.islandclash.commands;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    
    private final IslandClash module;

    public StatsCommand(final IslandClash module) {
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
                User user = (User) player.getMetadata("userData").get(0).value();
                
                player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
                player.sendMessage(module.getPrefix() + "§bSpieler §8» §7" + player.getName());
                player.sendMessage(module.getPrefix() + "§bPosition im Ranking §8» §7" + "FIIIIIIIIIIX");
                player.sendMessage(module.getPrefix() + "§bCoins §8» §7" + user.getCoins());
                player.sendMessage(module.getPrefix() + "§bKills §8» §7" + user.getKills());
                player.sendMessage(module.getPrefix() + "§bTode §8» §7" + user.getDeaths());
                //player.sendMessage(module.getPrefix() + "§bK/D §8» §7" + (Math.round((user.getKills() / user.getDeaths()) * 100) / 100.0));
                player.sendMessage(module.getPrefix() + "§bSiege §8» §7" + user.getWonGames());
                player.sendMessage(module.getPrefix() + "§bNiederlagen §8» §7" + user.getLostGames());
                player.sendMessage(module.getPrefix() + "§bUnentschieden §8» §7" + user.getUndecidedGames());
                //player.sendMessage(module.getPrefix() + "§bW/L §8» §7" + (Math.round((user.getWonGames() / user.getLostGames()) * 100) / 100.0));
                player.sendMessage(module.getPrefix() + "§bBlöcke gesetzt §8» §7" + user.getBlocksPlaced());
                player.sendMessage(module.getPrefix() + "§bBlöcke abgebaut §8» §7" + user.getBlocksBreaked());
                player.sendMessage(module.getPrefix() + "§bKisten geöffnet §8» §7" + user.getChestsOpend());
                player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
                
                break;
                
            case 1:
                break;
                
            default:
                player.sendMessage(module.getUnknownCommand());
                break;
            
        }
        
        return true;
    }

}
