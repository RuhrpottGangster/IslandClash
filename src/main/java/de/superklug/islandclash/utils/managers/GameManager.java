package de.superklug.islandclash.utils.managers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.Achievment;
import de.superklug.islandclash.utils.entities.Kit;
import de.superklug.islandclash.utils.entities.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class GameManager {
    
    private final IslandClash module;
    
    private final @Getter Map<String, Kit> availibleKits = Maps.newLinkedHashMap();
    private final @Getter Map<String, Achievment> availibleAchievments = Maps.newLinkedHashMap();
    private final @Getter Map<String, Team> availibleTeams = Maps.newLinkedHashMap();
    private final @Getter List<String> availibleMaps = Lists.newArrayList();
    
    private final @Getter Map<Player, Kit> activePlayerKit = Maps.newConcurrentMap();
    private final @Getter List<Player> hasSelectedTeam = Lists.newArrayList();
    private final @Getter List<Player> spectators = Lists.newArrayList();
    
    private final @Getter int maxPlayersATeam;
    private final @Getter int maxTeams;
    
    private @Getter @Setter boolean freezePlayers = false;
    private @Getter @Setter boolean godmodePlayers = true;

    public GameManager(final IslandClash module) {
        this.module = module;
        
        this.maxPlayersATeam = Integer.parseInt(module.getConfigManager().getConfiguration().getString("GameSettings.Variante").split("x")[1]);
        this.maxTeams = Integer.parseInt(module.getConfigManager().getConfiguration().getString("GameSettings.Variante").split("x")[0]);
        
        initializeKits();
        initializeAchievments();
        initializeTeams();
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="initializeKits">
    private void initializeKits() {
        
        {
            Kit kit = new Kit();

            kit.setName("Random");
            kit.setDescription(Arrays.asList("§8► §7Zufälliges §bKit"));

            kit.setItem(module.item(Material.BARRIER).build());
            kit.setPrice(0);

            kit.setArmorContents(null);
            kit.setInventoryContents(new ItemStack[]{});

            this.availibleKits.put(kit.getName(), kit);
        }
        
        {
            Kit kit = new Kit();
            kit.setName("Starter");
            kit.setDescription(Arrays.asList("§8► §7Die §bAusrüstung§8:",
                    " §8(§b5x§8) §7Karotten"));
            
            kit.setItem(module.item(Material.WOOD_PICKAXE).build());
            kit.setPrice(0);
            
            kit.setArmorContents(null);
            kit.setInventoryContents(new ItemStack[]{
                module.item(Material.CARROT, 5).build()
            });
            
            this.availibleKits.put(kit.getName(), kit);
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="initializeAchievments">
    private void initializeAchievments() {
        
        {
            Achievment achievment = new Achievment();
            
            achievment.setName("FirstKitBought");
            achievment.setDisplayName("Dein erstes Kit");
            achievment.setDescription("§7Kaufe dein §berstes §7Kit§8!");
            achievment.setReward(100);
            
            this.availibleAchievments.put(achievment.getName(), achievment);
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="initializeTeams">
    private void initializeTeams() {
        
        // Rot, Blau, Grün, Gelb, Lila, Gold, Türkis, Pink
        
        {
            Team team = new Team();
            
            team.setName("Rot");
            team.setPrefix("§c█ §8● §7");
            
            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());
            
            this.availibleTeams.put("Rot", team);
        }
        
        {
            Team team = new Team();
            
            team.setName("Blau");
            team.setPrefix("§9█ §8● §7");
            
            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());
            
            this.availibleTeams.put("Blau", team);
        }
        
        {
            Team team = new Team();
            
            team.setName("Grün");
            team.setPrefix("§a█ §8● §7");
            
            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());
            
            this.availibleTeams.put("Grün", team);
        }
        
        {
            Team team = new Team();
            
            team.setName("Gelb");
            team.setPrefix("§e█ §8● §7");
            
            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());
            
            this.availibleTeams.put("Gelb", team);
        }
        
        {
            Team team = new Team();
            
            team.setName("Orange");
            team.setPrefix("§6█ §8● §7");
            
            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());
            
            this.availibleTeams.put("Orange", team);
        }
        
        {
            Team team = new Team();
            
            team.setName("Lila");
            team.setPrefix("§5█ §8● §7");
            
            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());
            
            this.availibleTeams.put("Lila", team);
        }
        
        {
            Team team = new Team();

            team.setName("Pink");
            team.setPrefix("§d█ §8● §7");

            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());

            this.availibleTeams.put("Pink", team);
        }
        
        {
            Team team = new Team();
            
            team.setName("Türkis");
            team.setPrefix("§b█ §8● §7");
            
            team.setRoundKills(Maps.newConcurrentMap());
            team.setMembers(Lists.newArrayList());
            
            this.availibleTeams.put("Türkis", team);
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="chooseRandomMap">
    public void chooseRandomMap() {
        
        try {
            module.getConfigManager().getConfiguration().getConfigurationSection("Locations").getKeys(false).forEach((maps) -> {
                if(!maps.equalsIgnoreCase("Lobby_Spawn")) {
                    this.availibleMaps.add(maps);
                }
            });
            
            module.setMapName(this.availibleMaps.get(new Random().nextInt(this.availibleMaps.size())));
        } catch(Exception exception) {
            Bukkit.getConsoleSender().sendMessage(module.getPrefix() + "Es konnte keine §bzufällige Karte §7ausgewählt werden§8!");
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="playWinnerFirework">
    public void playWinnerFirework(final Player player) {
        
        module.runTaskLater(() -> {
            Firework firework = (Firework) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();
            
            fireworkBuilder.withTrail();
            fireworkBuilder.withFlicker();
            fireworkBuilder.withFade(Color.ORANGE);
            fireworkBuilder.withColor(Color.YELLOW, Color.RED);
            fireworkBuilder.with(FireworkEffect.Type.BALL_LARGE);
            
            meta.addEffect(fireworkBuilder.build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }, 5);
        
        module.runTaskLater(() -> {
            Firework firework = (Firework) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();
            
            fireworkBuilder.withTrail();
            fireworkBuilder.withFlicker();
            fireworkBuilder.withFade(Color.ORANGE);
            fireworkBuilder.withColor(Color.YELLOW, Color.RED);
            fireworkBuilder.with(FireworkEffect.Type.BALL_LARGE);
            
            meta.addEffect(fireworkBuilder.build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }, 10);
        
        module.runTaskLater(() -> {
            Firework firework = (Firework) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();
            
            fireworkBuilder.withTrail();
            fireworkBuilder.withFlicker();
            fireworkBuilder.withFade(Color.ORANGE);
            fireworkBuilder.withColor(Color.YELLOW, Color.RED);
            fireworkBuilder.with(FireworkEffect.Type.BALL_LARGE);
            
            meta.addEffect(fireworkBuilder.build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }, 15);
        module.runTaskLater(() -> {
            Firework firework = (Firework) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();
            
            fireworkBuilder.withTrail();
            fireworkBuilder.withFlicker();
            fireworkBuilder.withFade(Color.ORANGE);
            fireworkBuilder.withColor(Color.YELLOW, Color.RED);
            fireworkBuilder.with(FireworkEffect.Type.BALL_LARGE);
            
            meta.addEffect(fireworkBuilder.build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }, 20);
        
        module.runTaskLater(() -> {
            Firework firework = (Firework) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();
            
            fireworkBuilder.withTrail();
            fireworkBuilder.withFlicker();
            fireworkBuilder.withFade(Color.ORANGE);
            fireworkBuilder.withColor(Color.YELLOW, Color.RED);
            fireworkBuilder.with(FireworkEffect.Type.BALL_LARGE);
            
            meta.addEffect(fireworkBuilder.build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }, 25);
        
        module.runTaskLater(() -> {
            Firework firework = (Firework) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();
            
            fireworkBuilder.withTrail();
            fireworkBuilder.withFlicker();
            fireworkBuilder.withFade(Color.ORANGE);
            fireworkBuilder.withColor(Color.YELLOW, Color.RED);
            fireworkBuilder.with(FireworkEffect.Type.BALL_LARGE);
            
            meta.addEffect(fireworkBuilder.build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }, 30);
        
    }
    //</editor-fold>
    
    public void checkWinner() {
        // Map Rekord mit einbauen + am Ende eine Nachricht wenn geupdatet
    }

}
