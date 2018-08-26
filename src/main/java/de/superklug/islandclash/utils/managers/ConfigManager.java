package de.superklug.islandclash.utils.managers;

import de.superklug.islandclash.IslandClash;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    
    private final IslandClash module;
    
    private final File path = new File("plugins/IslandClash");
    private final @Getter File file = new File(path, "config.yml");
    
    private final @Getter YamlConfiguration configuration = YamlConfiguration.loadConfiguration(this.file);

    public ConfigManager(final IslandClash module) {
        this.module = module;
        
        initializeConfig();
    }
    
    private void initializeConfig() {
        
        if(!this.path.exists()) {
            this.path.mkdir();
        }
        
        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.configuration.options().copyDefaults(true);
                this.configuration.addDefault("GameSettings.Variante", "8x1");
                this.configuration.save(this.file);
            } catch (IOException ex) {
                Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
