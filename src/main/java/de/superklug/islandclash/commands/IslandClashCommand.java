package de.superklug.islandclash.commands;

import de.superklug.islandclash.IslandClash;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandClashCommand implements CommandExecutor {
    
    private final IslandClash module;

    public IslandClashCommand(final IslandClash module) {
        this.module = module;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cykaBlyat, String label, String[] arguments) {
        
        if(!(sender instanceof Player)) {
            sender.sendMessage(label);
            return true;
        }
        Player player = (Player) sender;
        String command = arguments[0];
        
        if(player.hasPermission("IslandClash.Owner")) {
            
            switch(arguments.length) {
                
                case 0:
                    sendInfo(player);
                    break;
                    
                case 1:
                    
                    if(command.equalsIgnoreCase("setLobby")) {
                        module.getLocationManager().setLocation("Lobby_Spawn", 0, player.getLocation());
                        
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 10F);
                        player.sendTitle("§8❱ §a✔ §8❰", "§bWartelobby §7gesetzt§8!");
                        player.sendMessage(module.getPrefix() + "§bWartelobby §7erfolgreich gesetzt§8!");
                    }
                    
                    else if(command.equalsIgnoreCase("setPos")) {
                       player.sendMessage(module.getPrefix() + "Du musst eine §bKarte §7angeben§8, §7um einen §bSpawnpunkt §7zu setzten§8!");
                    }
                    
                    else {
                        sendInfo(player);
                    }
                    
                    break;
                    
                case 2:
                    
                    if(command.equalsIgnoreCase("setPos")) {
                        player.sendMessage(module.getPrefix() + "Du musst eine §bSpawnpunkt§8-§bID §7angeben§8, §7um einen §bSpawnpunkt §7zu setzten§8!");
                    }
                    
                    else {
                        sendInfo(player);
                    }
                    
                    break;
                    
                case 3:
                    
                    if(command.equalsIgnoreCase("setPos")) {
                        module.getLocationManager().setLocation(arguments[1], Integer.parseInt(arguments[2]), player.getLocation());

                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 10F);
                        player.sendTitle("§8❱ §a✔ §8▍ §b" + arguments[1] + " §8❰", "§7Spawnpunkt §b" + arguments[2] + " §7gesetzt§8!");
                        player.sendMessage(module.getPrefix() + "§7Spawnpunkt §b" + arguments[2] + " §7auf der Karte §b" + arguments[1] + " §7erfolgreich gesetzt§8!");
                    }
                    
                    else {
                        sendInfo(player);
                    }
                    
                    break;
                    
                default:
                    sendInfo(player);
                    break;
                
            }
            
        } else {
            player.sendMessage(module.getNoPermissions());
        }
        
        return true;
    }
    
    private void sendInfo(final Player player) {
        player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
        player.sendMessage(module.getPrefix() + "§b" + "/is setLobby" + " §8» §7" + "Wartelobby setzten");
        player.sendMessage(module.getPrefix() + "§b" + "/is setPos §8<§7Karte§8> §8<§7ID§8>" + " §8» §7" + "Spawnpunkte setzten");
        player.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
    }

}
