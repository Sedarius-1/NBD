package org.ibd.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.purchases.PurchaseMap;
import org.ibd.model.weapons.*;

import java.util.ArrayList;
import java.util.Objects;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.ibd.repository.BongoConfig.*;

public class WeaponRepository implements Repository<WeaponMap>, AutoCloseable {

    private final String weaponCollectionName = "weaponCollection";

    private final MongoClient mongoClient;

    private final MongoDatabase database;

    public WeaponRepository() {
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
            if (name.equals(weaponCollectionName)) {
                this.database.getCollection(name).drop();
                break;
            }
        }

        // Create test collection
        this.database.createCollection(weaponCollectionName);
    }

    //Create
    @Override
    public void add(WeaponMap weaponMap) throws RepositoryException {
        try {
            MongoCollection<WeaponMap> testDBItemMongoCollection =
                    this.database.getCollection(weaponCollectionName, WeaponMap.class);
            testDBItemMongoCollection.insertOne(weaponMap);
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }

    }

    //Read
    @Override
    public final WeaponMap get(Long id) throws RepositoryException {
        try {
            return this.database
                    .getCollection(weaponCollectionName, WeaponMap.class)
                    .find(eq("serialNumber", id))
                    .into(new ArrayList<>())
                    .getFirst();
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }

    @Override
    public ArrayList<WeaponMap> find(org.bson.conversions.Bson bson) {

        return this.database
                .getCollection(weaponCollectionName, WeaponMap.class)
                .find(bson)
                .into(new ArrayList<>());
    }

    @Override
    public ArrayList<WeaponMap> getAll() {
        MongoCollection<WeaponMap> testDBItemMongoCollection =
                this.database.getCollection(weaponCollectionName, WeaponMap.class);
        return testDBItemMongoCollection.find().into(new ArrayList<>());
    }

    //Update
    @Override
    public boolean updateOne(Long id, org.bson.conversions.Bson updater) {
        try {
            database
                    .getCollection(weaponCollectionName, WeaponMap.class)
                    .updateOne(eq("serialNumber", id), updater);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    //Delete
    @Override
    public void remove(Long id) throws RepositoryException {
        try {
            WeaponMap weaponMap = get(id);
            if (Objects.isNull(weaponMap)) throw new RepositoryException("No such weapon exists!");
            database
                    .getCollection(weaponCollectionName, WeaponMap.class)
                    .deleteOne(eq("serialNumber", id));
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }

    @Override
    public void close() {
        this.mongoClient.close();
    }
}
