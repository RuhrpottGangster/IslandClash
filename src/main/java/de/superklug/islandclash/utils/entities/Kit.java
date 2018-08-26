package de.superklug.islandclash.utils.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class Kit {
    
    private @Getter @Setter String name;
    private @Getter @Setter List<String> description;
    private @Getter @Setter ItemStack item;
    private @Getter @Setter int price;
    
    private @Getter @Setter ItemStack[] armorContents;
    private @Getter @Setter ItemStack[] inventoryContents;

    public Kit() {
    }

}
