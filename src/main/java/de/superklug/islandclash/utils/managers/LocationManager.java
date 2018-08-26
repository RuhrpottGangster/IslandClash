package de.superklug.islandclash.utils.managers;

import com.google.common.collect.Lists;
import de.superklug.islandclash.IslandClash;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationManager {
    
    private final IslandClash module;

    public LocationManager(final IslandClash module) {
        this.module = module;
    }
    
    public List<Location> getLocations(final String mapName) {
        List<Location> list = Lists.newLinkedList();
        
        module.getConfigManager().getConfiguration().getConfigurationSection("Locations." + mapName).getKeys(false).forEach((pathNumber) -> {
            
            Location location = new Location(Bukkit.getWorld(module.getConfigManager().getConfiguration().getString("Locations." + mapName + "." + pathNumber + ".World")),
                    module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + pathNumber + ".X"),
                    module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + pathNumber + ".Y"),
                    module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + pathNumber + ".Z"),
                    (float) module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + pathNumber + ".Yaw"),
                    (float) module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + pathNumber + ".Pitch"));
            
            list.add(location);
            
        });
        
        return list;
    }
    
    public Location getLocation(final String mapName, final int number) {
        
        Location location = new Location(Bukkit.getWorld(module.getConfigManager().getConfiguration().getString("Locations." + mapName + "." + number + ".World")),
                module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + number + ".X"),
                module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + number + ".Y"),
                module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + number + ".Z"),
                (float) module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + number + ".Yaw"),
                (float) module.getConfigManager().getConfiguration().getDouble("Locations." + mapName + "." + number + ".Pitch"));

        return location;
    }
    
    public void setLocation(final String mapName, final int number, final Location location) {
        
        module.getConfigManager().getConfiguration().set("Locations." + mapName + ".Builders", "???");
        module.getConfigManager().getConfiguration().set("Locations." + mapName + ".Bewertung", "⭐");
        module.getConfigManager().getConfiguration().set("Locations." + mapName + "." + number + ".World", location.getWorld().getName());
        module.getConfigManager().getConfiguration().set("Locations." + mapName + "." + number + ".X", location.getX());
        module.getConfigManager().getConfiguration().set("Locations." + mapName + "." + number + ".Y", location.getY());
        module.getConfigManager().getConfiguration().set("Locations." + mapName + "." + number + ".Z", location.getZ());
        module.getConfigManager().getConfiguration().set("Locations." + mapName + "." + number + ".Yaw", location.getYaw());
        module.getConfigManager().getConfiguration().set("Locations." + mapName + "." + number + ".Pitch", location.getPitch());
        module.getConfigManager().save();
        
    }

}
