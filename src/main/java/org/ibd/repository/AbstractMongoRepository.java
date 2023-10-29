package org.ibd.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.ibd.model.TestDBItem;

import java.util.ArrayList;
import java.util.List;

// TODO: Make this class abstract
// TODO: add real repository functionality
// TODO: make other repositories inherit from AbstractMongoRepository
public class AbstractMongoRepository implements AutoCloseable {
    private final ConnectionString connectionString = new ConnectionString(
            "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=mongo_repl");
    private final MongoCredential credential = MongoCredential.createCredential(
            "admin", "admin", "password".toCharArray());

    private final String collectionName = "testCollection";

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());

    private MongoClient mongoClient;
    private MongoDatabase database;

    public AbstractMongoRepository() {
        // Init DB connection
        this.initDbConnection();

        // Delete test collection, if it exists
        MongoIterable<String> list = this.database.listCollectionNames();
        for (String name : list) {
            if (name.equals(collectionName)) {
                this.database.getCollection(name).drop();
                break;
            }
        }

        // Create test collection
        this.database.createCollection(collectionName);
    }

    void initDbConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        // CodecRegistries.fromProviders(new UniqueIdCodecProvider()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("admin");
    }

    public void testAdd(TestDBItem testDBItem) {
        MongoCollection<TestDBItem> testDBItemMongoCollection =
                this.database.getCollection(collectionName, TestDBItem.class);
        testDBItemMongoCollection.insertOne(testDBItem);
    }

    public ArrayList<TestDBItem> testGet() {
        MongoCollection<TestDBItem> testDBItemMongoCollection =
                this.database.getCollection(collectionName, TestDBItem.class);
        return testDBItemMongoCollection.find().into(new ArrayList<>());
    }


    @Override
    public void close() {
        this.mongoClient.close();
    }
}
