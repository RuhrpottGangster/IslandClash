package de.superklug.islandclash.utils.managers;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import de.superklug.islandclash.IslandClash;
import lombok.Getter;
import org.bson.Document;

public class MongoManager {
    
    private final IslandClash module;
    
    private final String hostname;
    private final int port;
    
    private MongoClient client;
    private MongoDatabase database;
    
    private @Getter MongoCollection<Document> users;

    public MongoManager(final IslandClash module, final String hostname, final int port) {
        this.module = module;
        
        this.hostname = hostname;
        this.port = port;
        
        connect();
    }
    
    private void connect() {
        this.client = MongoClients.create(new ConnectionString(module.format("mongodb://{0}:{1}", hostname, String.valueOf(port))));
        this.database = this.client.getDatabase("islandClash");
        
        this.users = this.database.getCollection("users");
    }

}
