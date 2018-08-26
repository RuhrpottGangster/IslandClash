package de.superklug.islandclash.utils.managers;

import com.google.common.collect.Maps;
import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.enums.InventoryType;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class InventoryManager {
    
    private final IslandClash module;
    
    private final @Getter Map<InventoryType, Inventory> inventories = Maps.newConcurrentMap();

    public InventoryManager(final IslandClash module) {
        this.module = module;
        
        initializeInventories();
    }
    
    private void initializeInventories() {
        
        {
            Inventory inventory = Bukkit.createInventory(null, 9);
            
            inventory.setItem(0, module.item(Material.CHEST)
                    .setDisplayname("§3•§b● Kitauswahl §8▍ §7Rechtsklick")
                    .setLore("§8► §7Benutze eines deiner freigeschaltenen §bKits§8!")
                    .build());
            
            inventory.setItem(1, module.item(Material.BED)
                    .setDisplayname("§3•§b● Teamauswahl §8▍ §7Rechtsklick")
                    .setLore("§8► §7Trete einem §bTeam §7bei§8!")
                    .build());
            
            inventory.setItem(4, module.item(Material.COMMAND)
                    .setDisplayname("§3•§b● Runden Einstellungen §8▍ §7Rechtsklick")
                    .setLore("§8► §7Ändere die §bKarte §7und §bstarte §7die Runde§8!")
                    .build());
            
            inventory.setItem(7, module.item(Material.NETHER_STAR)
                    .setDisplayname("§3•§b● Erfolge §8▍ §7Rechtsklick")
                    .setLore("§8► §7Sehe deine abgeschlossenen §bErfolge §7an§8!")
                    .build());
            
            inventory.setItem(8, module.item(Material.MAGMA_CREAM)
                    .setDisplayname("§3•§b● Verlassen §8▍ §7Rechtsklick")
                    .setLore("§8► §7Kehre zurück zur §bLobby§8!")
                    .build());
            
            this.inventories.put(InventoryType.HOTBAR, inventory);
        }
        
        {
            Inventory inventory = Bukkit.createInventory(null, 27, "§3•§b● Runden Einstellungen");
            
            for(int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, module.item(Material.STAINED_GLASS_PANE, 1, (short) 7).setNoName().build());
            }

            inventory.setItem(9+3, module.item(Material.MAP)
                    .setDisplayname("§3•§b● Kartenauswahl §8▍ §7ForceMap")
                    .setLore("§8► §7Ändere die §bKarte §7zu deiner lieblings Karte§8!")
                    .build());

            inventory.setItem(9+5, module.item(Material.WATCH)
                    .setDisplayname("§3•§b● Schenllstart §8▍ §7ForceStart")
                    .setLore("§8► §7Verkürze die §bWartezeit §7und starte das Spiel§8!")
                    .build());

            this.inventories.put(InventoryType.ROUNDSETTINGS, inventory);
        }
        
    }
    
    public Inventory getInventory(final InventoryType inventoryType) {
        Inventory inventory = this.inventories.get(inventoryType);
        
        assert inventory != null;
        
        return inventory;
    }

}
