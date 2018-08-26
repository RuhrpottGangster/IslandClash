package de.superklug.islandclash.commands;

import de.superklug.islandclash.IslandClash;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SwitchMapCommand implements CommandExecutor {

    private final IslandClash module;

    public SwitchMapCommand(final IslandClash module) {
        this.module = module;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(label);
            return true;
        }
        Player player = (Player) sender;
        
        if(!player.hasPermission("IslandClash.Owner") | !player.hasPermission("IslandClash.Administrator")) {
            player.sendMessage(module.getNoPermissions());
            return true;
        }
        
        switch(arguments.length) {
            
            case 0:
                player.sendMessage(module.getPrefix() + "Gebe einen §bKartennamen §7an§8, §7um dich zu teleportieren§8!");
                break;
                
            case 1:
                Bukkit.createWorld(new WorldCreator(arguments[0]));
                
                if(Bukkit.getServer().getWorld(arguments[0]) == null) {
                    player.sendMessage(module.getPrefix() + "Die Karte §b" + arguments[0] + " §7wurde nicht gefunden§8.");
                    return true;
                }
                
                double x = Bukkit.getWorld(arguments[0]).getSpawnLocation().getX();
                double y = Bukkit.getWorld(arguments[0]).getSpawnLocation().getY();
                double z = Bukkit.getWorld(arguments[0]).getSpawnLocation().getZ();
                float yaw = Bukkit.getWorld(arguments[0]).getSpawnLocation().getYaw();
                float pitch = Bukkit.getWorld(arguments[0]).getSpawnLocation().getPitch();

                player.teleport(new Location(Bukkit.getWorld(arguments[0]), x, y, z, yaw, pitch));
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 0);
                player.sendMessage(module.getPrefix() + "Du hast die Karte §b" + arguments[0] + " §7betreten§8.");
                break;
            
            default:
                player.sendMessage(module.getUnknownCommand());
                break;
            
        }
        
        return true;
    }
        
}
