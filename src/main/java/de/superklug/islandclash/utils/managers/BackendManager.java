package de.superklug.islandclash.utils.managers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.Block;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BackendManager {
    
    private final IslandClash module;

    public BackendManager(final IslandClash module) {
        this.module = module;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getTopUser">
    public void getTopUser(final int limit, Consumer<List<User>> consumer) {
        final List<User> list = Lists.newArrayList();
        
        Block<Document> block = (Document document) -> {
            User user = module.getGson().fromJson(document.toJson(), User.class);
            list.add(user);
        };
        
        module.getMongoManager().getUsers().find().limit(limit).sort(Filters.eq("wonGames", -1)).forEach(block, (Void t, Throwable thrwbl) -> {
            consumer.accept(list);
        });
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getTopUser">
    private int topUserI = 0;
    public void getTopUser(Consumer<Map<User, Integer>> consumer) {
        final Map<User, Integer> map = Maps.newConcurrentMap();
        
        Block<Document> block = (Document document) -> {
            User user = module.getGson().fromJson(document.toJson(), User.class);
            map.put(user, topUserI);
            topUserI++;
        };

        module.getMongoManager().getUsers().find().sort(Filters.eq("wonGames", -1)).forEach(block, (Void t, Throwable thrwbl) -> {
            topUserI = 0;
            consumer.accept(map);
        });

    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getUser">
    public void getUser(final String uuid, Consumer<User> consumer) {
        Player player = Bukkit.getServer().getPlayer(UUID.fromString(uuid));
        
        if(player.isOnline()) {
            if(player.hasMetadata("userData")) {
                consumer.accept((User) player.getMetadata("userData").get(0).value());
                return;
            }
        }
        
        module.getMongoManager().getUsers().find(Filters.eq("uuid", uuid)).first((Document t, Throwable thrwbl) -> {
            
            if(t == null) {
                User user = new User();
                user.setUuid(uuid);
                
                if(player.isOnline()) {
                    user.setName(player.getName());
                } else {
                    user.setName("???");
                }
                
                user.setCoins(0);
                user.setKills(0);
                user.setDeaths(0);
                user.setWonGames(0);
                user.setLostGames(0);
                user.setUndecidedGames(0);
                user.setBlocksPlaced(0);
                user.setBlocksBreaked(0);
                user.setChestsOpend(0);
                user.setKitsBought(Lists.newArrayList());
                user.setAchievmentsReceived(Lists.newArrayList());
                user.setMapRecord(Maps.newConcurrentMap());
                
                t = module.getGson().fromJson(module.getGson().toJson(user), Document.class);
                
                module.getMongoManager().getUsers().insertOne(t, (Void t1, Throwable thrwbl1) -> {
                    
                    if(player.isOnline()) {
                        module.setMetadata(player, "userData", user);
                    }
                    
                    consumer.accept(user);
                });
                return;
            }
            
            User user = module.getGson().fromJson(t.toJson(), User.class);
            
            if(player.isOnline()) {
                module.setMetadata(player, "userData", user);
            }
            
            consumer.accept(user);
            
        });
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="updateUser">
    public void updateUser(final User user, Consumer<User> consumer) {
        Document document = module.getGson().fromJson(module.getGson().toJson(user), Document.class);
        
        module.getMongoManager().getUsers().replaceOne(Filters.eq("uuid", user.getUuid()), document, (UpdateResult t, Throwable thrwbl) -> {
            Player player = Bukkit.getServer().getPlayer(UUID.fromString(user.getUuid()));
            
            if(player.isOnline()) {
                module.setMetadata(player, "userData", user);
            }
            
            consumer.accept(user);
        });
        
    }
    //</editor-fold>

}
