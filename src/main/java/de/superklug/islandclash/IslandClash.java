package de.superklug.islandclash;

import com.google.gson.Gson;
import de.superklug.islandclash.commands.FixCommand;
import de.superklug.islandclash.commands.IslandClashCommand;
import de.superklug.islandclash.commands.StatsCommand;
import de.superklug.islandclash.commands.SwitchMapCommand;
import de.superklug.islandclash.commands.TopCommand;
import de.superklug.islandclash.listeners.AchievmentReceiveListener;
import de.superklug.islandclash.listeners.KitBuyListener;
import de.superklug.islandclash.listeners.PlayerJoinListener;
import de.superklug.islandclash.listeners.PlayerKickListener;
import de.superklug.islandclash.listeners.PlayerQuitListener;
import de.superklug.islandclash.utils.builders.HologramBuilder;
import de.superklug.islandclash.utils.builders.ItemBuilder;
import de.superklug.islandclash.utils.countdowns.EndingPhaseCountdown;
import de.superklug.islandclash.utils.countdowns.IngamePhaseCountdown;
import de.superklug.islandclash.utils.countdowns.LobbyPhaseCountdown;
import de.superklug.islandclash.utils.countdowns.WarmupPhaseCountdown;
import de.superklug.islandclash.utils.entities.mojang.ReflectionUtil;
import de.superklug.islandclash.utils.enums.GameState;
import de.superklug.islandclash.utils.managers.BackendManager;
import de.superklug.islandclash.utils.managers.ConfigManager;
import de.superklug.islandclash.utils.managers.GameManager;
import de.superklug.islandclash.utils.managers.InventoryManager;
import de.superklug.islandclash.utils.managers.LocationManager;
import de.superklug.islandclash.utils.managers.MongoManager;
import de.superklug.islandclash.utils.scoreboard.PlayerScoreboard;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class IslandClash extends JavaPlugin {
    
    private final @Getter Gson gson = new Gson();
    private final @Getter ExecutorService executorService = Executors.newCachedThreadPool();
    private final @Getter DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    private final @Getter ReflectionUtil reflectionUtil = new ReflectionUtil(this);
    
    private final @Getter MongoManager mongoManager = new MongoManager(this, "127.0.0.1", 27017);
    private final @Getter ConfigManager configManager = new ConfigManager(this);
    private final @Getter BackendManager backendManager = new BackendManager(this);
    private final @Getter InventoryManager inventoryManager = new InventoryManager(this);
    private final @Getter GameManager gameManager = new GameManager(this);
    private final @Getter LocationManager locationManager = new LocationManager(this);
    
    private final @Getter PlayerScoreboard playerScoreboard = new PlayerScoreboard(this);
    
    private final LobbyPhaseCountdown lobbyPhaseCountdown = new LobbyPhaseCountdown(this);
    private final WarmupPhaseCountdown warmupPhaseCountdown = new WarmupPhaseCountdown(this);
    private final IngamePhaseCountdown ingamePhaseCountdown = new IngamePhaseCountdown(this);
    private final EndingPhaseCountdown endingPhaseCountdown = new EndingPhaseCountdown(this);
    
    private final @Getter String prefix = "§3•§b● IslandClash §8▍ §7";
    private final @Getter String noPermissions = prefix + "Du hast keinen Zugriff auf diesen Befehl.";
    private final @Getter String unknownCommand = prefix + "Dieser Befehl existiert nicht.";
    
    private @Getter @Setter int remainingRoundTime = ingamePhaseCountdown.getTime();
    private @Getter @Setter GameState gameState = GameState.LOBBY;
    
    private @Getter @Setter String mapName;

    @Override
    public void onEnable() {
        init();
        
        Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
            removeMetadata(players, "userData");
            players.kickPlayer(this.prefix + "Der Server macht einen kurzen Neustart§8!");
        });
        
        Bukkit.getServer().getWorlds().forEach((worlds) -> {
            worlds.setAutoSave(false);
        });
        
        Bukkit.getConsoleSender().sendMessage(format(prefix + "Das Plugin von §b{0} §7wurde §ageladen§8!", Bukkit.getOfflinePlayer(UUID.fromString("a1d03d2d-6637-4fcd-881e-fe1c3b22fd6c")).getName()));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(format(prefix + "Das Plugin von §b{0} §7wurde §cbeendet§8!", Bukkit.getOfflinePlayer(UUID.fromString("a1d03d2d-6637-4fcd-881e-fe1c3b22fd6c")).getName()));
    }
    
    private void init() {
        this.gameManager.chooseRandomMap();
        
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerKickListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        
        pluginManager.registerEvents(new KitBuyListener(this), this);
        pluginManager.registerEvents(new AchievmentReceiveListener(this), this);
        
        getCommand("islandclash").setExecutor(new IslandClashCommand(this));
        getCommand("switchmap").setExecutor(new SwitchMapCommand(this));
        getCommand("fix").setExecutor(new FixCommand(this));
        getCommand("top").setExecutor(new TopCommand(this));
        getCommand("stats").setExecutor(new StatsCommand(this));
        
        this.playerScoreboard.animate();
    }
    
    //<editor-fold defaultstate="collapsed" desc="format">
    public String format(final String pattern, final Object... objects) {
        final String string = MessageFormat.format(pattern, objects);

        assert string != null;

        return string;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="actionbar">
    public void actionbar(final Player player, final String message) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="runTask">
    public BukkitTask runTask(Runnable runnable) {
        return getServer().getScheduler().runTask(this, runnable);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="runTaskAsync">
    public BukkitTask runTaskAsync(Runnable runnable) {
        return getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="runTaskLater">
    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        return getServer().getScheduler().runTaskLater(this, runnable, delay);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="runTaskLaterAsync">
    public BukkitTask runTaskLaterAsync(Runnable runnable, long delay) {
        return getServer().getScheduler().runTaskLaterAsynchronously(this, runnable, delay);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="runTaskTimer">
    public BukkitTask runTaskTimer(Runnable runnable, long delay, long repeat) {
        return getServer().getScheduler().runTaskTimer(this, runnable, delay, repeat);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="runTaskTimerAsync">
    public BukkitTask runTaskTimerAsync(Runnable runnable, long delay, long repeat) {
        return getServer().getScheduler().runTaskTimerAsynchronously(this, runnable, delay, repeat);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setMetadata">
    public void setMetadata(final Player player, final String name, final Object value) {
        removeMetadata(player, name);
        player.setMetadata(name, new FixedMetadataValue(this, value));
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="removeMetadata">
    public void removeMetadata(final Player player, final String name) {
        if(player.hasMetadata(name)) {
            player.removeMetadata(name, this);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="item">
    public ItemBuilder item(final Material type, final int amount, final short damage) {
        return new ItemBuilder(this, type, amount, damage);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="item">
    public ItemBuilder item(final Material type, final int amount) {
        return new ItemBuilder(this, type, amount);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="item">
    public ItemBuilder item(final Material type) {
        return new ItemBuilder(this, type);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="hologram">
    public HologramBuilder hologram(final Location location, final String... lines) {
        return new HologramBuilder(this, location, lines);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="startLobbyPhaseCountdown">
    public void startLobbyPhaseCountdown() {
        this.lobbyPhaseCountdown.runTaskTimer(this, 20, 20);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="startWarmupPhaseCountdown">
    public void startWarmupPhaseCountdown() {
        this.warmupPhaseCountdown.runTaskTimer(this, 20, 20);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="startIngamePhaseCountdown">
    public void startIngamePhaseCountdown() {
        this.ingamePhaseCountdown.runTaskTimer(this, 20, 20);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="startEndingPhaseCountdown">
    public void startEndingPhaseCountdown() {
        this.endingPhaseCountdown.runTaskTimer(this, 20, 20);
    }
    //</editor-fold>

}
