package org.ibd.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.conversions.Bson;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.purchases.PurchaseMap;

import java.util.ArrayList;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static org.ibd.repository.BongoConfig.*;


public class PurchaseRepository implements Repository<PurchaseMap>, AutoCloseable {

    private final String purchaseCollectionName = "purchaseCollection";

    public ClientSession getMongoClientSession() {
        return mongoClient.startSession();
    }

    private final MongoClient mongoClient;

    private final MongoDatabase database;

    public PurchaseRepository() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("admin");
        // Delete test collection, if it exists
        MongoIterable<String> list = this.database.listCollectionNames();
        for (String name : list) {
            if (name.equals(purchaseCollectionName)) {
                this.database.getCollection(name).drop();
                break;
            }
        }

        // Create test collection
        this.database.createCollection(purchaseCollectionName);
    }

    //Create
    @Override
    public void add(PurchaseMap purchaseMap) throws RepositoryException {
        try {
            MongoCollection<PurchaseMap> testDBItemMongoCollection =
                    this.database.getCollection(purchaseCollectionName, PurchaseMap.class);
            testDBItemMongoCollection.insertOne(purchaseMap);
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }

    @Override
    public final PurchaseMap get(Long id) throws RepositoryException {
        try {
            return this.database
                    .getCollection(purchaseCollectionName, PurchaseMap.class)
                    .find(eq("purchaseId", id))
                    .into(new ArrayList<>())
                    .getFirst();
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }

    @Override
    public ArrayList<PurchaseMap> find(Bson bson) {
        return this.database
                .getCollection(purchaseCollectionName, PurchaseMap.class)
                .find(bson)
                .into(new ArrayList<>());
    }

    @Override
    public ArrayList<PurchaseMap> getAll() {
        MongoCollection<PurchaseMap> testDBItemMongoCollection =
                this.database.getCollection(purchaseCollectionName, PurchaseMap.class);
        return testDBItemMongoCollection.find().into(new ArrayList<>());

    }

    //Update
    @Override
    public boolean updateOne(Long id, Bson updater) {
        try {
            database
                    .getCollection(purchaseCollectionName, Purchase.class)
                    .updateOne(eq("purchaseId", id), updater);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    //Delete
    @Override
    public void remove(Long id) throws RepositoryException {
        try {
            PurchaseMap purchaseMap = get(id);
            database
                    .getCollection(purchaseCollectionName, Purchase.class)
                    .deleteOne(eq("purchaseId", id));
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }

    @Override
    public void close() {
        this.mongoClient.close();
    }
}
