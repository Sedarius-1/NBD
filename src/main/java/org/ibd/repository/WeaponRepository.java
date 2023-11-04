package org.ibd.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.*;

import java.util.ArrayList;
import java.util.Objects;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.ibd.repository.BongoConfig.*;

public class WeaponRepository implements Repository<Weapon>, AutoCloseable {

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
    public void add(Weapon weapon) throws RepositoryException {
        try{
            MongoCollection<Weapon> testDBItemMongoCollection =
                    this.database.getCollection(weaponCollectionName, Weapon.class);
            testDBItemMongoCollection.insertOne(weapon);
        }
        catch (Exception ex){
            throw new RepositoryException(ex.toString());
        }

    }
    //Read
    @Override
    public Weapon get(Long id) throws RepositoryException {

        MongoCollection<Pistol> pistolDbCollection =
                this.database.getCollection(weaponCollectionName, Pistol.class);
        ArrayList<Pistol> pistolList = pistolDbCollection
                .find(and(eq("type", "Pistol"), eq("serialNumber", id)))
                .into(new ArrayList<>());
        if(!pistolList.isEmpty()) return pistolList.getFirst();

        MongoCollection<Rifle> rifleDbCollection =
                this.database.getCollection(weaponCollectionName, Rifle.class);
        ArrayList<Rifle> rifleList = rifleDbCollection
                .find(and(eq("type", "Rifle"), eq("serialNumber", id)))
                .into(new ArrayList<>());
        if(!rifleList.isEmpty()) return rifleList.getFirst();

        MongoCollection<RecreationalMcNuke> nukeDbCollection =
                this.database.getCollection(weaponCollectionName, RecreationalMcNuke.class);
        ArrayList<RecreationalMcNuke> nukeList = nukeDbCollection
                .find(and(eq("type", "Nuke"), eq("serialNumber", id)))
                .into(new ArrayList<>());
        if(!nukeList.isEmpty()) return nukeList.getFirst();

        MongoCollection<HandGrenade> grenadeDbCollection =
                this.database.getCollection(weaponCollectionName, HandGrenade.class);
        ArrayList<HandGrenade> grenadeList = grenadeDbCollection
                .find(and(eq("type", "HandGrenade"), eq("serialNumber", id)))
                .into(new ArrayList<>());
        if (!grenadeList.isEmpty()) return grenadeList.getFirst();
        else{
            throw new RepositoryException("NO WEAPON WITH GIVEN SERIAL NUMBER FOUND");
        }
    }

    @Override
    public ArrayList<Weapon> find(org.bson.conversions.Bson bson) {

        ArrayList<Weapon> combinedList = new ArrayList<>();

        MongoCollection<Pistol> pistolDbCollection =
                this.database.getCollection(weaponCollectionName, Pistol.class);
        ArrayList<Pistol> pistolList = pistolDbCollection
                .find(and(eq("type", "Pistol"), bson))
                .into(new ArrayList<>());
        if(!pistolList.isEmpty()) combinedList.addAll(pistolList);

        MongoCollection<Rifle> rifleDbCollection =
                this.database.getCollection(weaponCollectionName, Rifle.class);
        ArrayList<Rifle> rifleList = rifleDbCollection
                .find(and(eq("type", "Rifle"), bson))
                .into(new ArrayList<>());
        if(!rifleList.isEmpty()) combinedList.addAll(rifleList);

        MongoCollection<RecreationalMcNuke> nukeDbCollection =
                this.database.getCollection(weaponCollectionName, RecreationalMcNuke.class);
        ArrayList<RecreationalMcNuke> nukeList = nukeDbCollection
                .find(and(eq("type", "Nuke"), bson))
                .into(new ArrayList<>());
        if(!nukeList.isEmpty()) combinedList.addAll(nukeList);

        MongoCollection<HandGrenade> grenadeDbCollection =
                this.database.getCollection(weaponCollectionName, HandGrenade.class);
        ArrayList<HandGrenade> grenadeList = grenadeDbCollection
                .find(and(eq("type", "HandGrenade"), bson))
                .into(new ArrayList<>());
        if(!nukeList.isEmpty()) combinedList.addAll(grenadeList);

        return combinedList;
    }

    @Override
    public ArrayList<Weapon> getAll() {
        MongoCollection<Pistol> pistolDbCollection =
            this.database.getCollection(weaponCollectionName, Pistol.class);
        ArrayList<Pistol> pistolList = pistolDbCollection.find(eq("type", "Pistol")).into(new ArrayList<>());

        MongoCollection<Rifle> rifleDbCollection =
                this.database.getCollection(weaponCollectionName, Rifle.class);
        ArrayList<Rifle> rifleList = rifleDbCollection.find(eq("type", "Rifle")).into(new ArrayList<>());

        MongoCollection<RecreationalMcNuke> nukeDbCollection =
                this.database.getCollection(weaponCollectionName, RecreationalMcNuke.class);
        ArrayList<RecreationalMcNuke> nukeList = nukeDbCollection.find(eq("type", "Nuke")).into(new ArrayList<>());

        MongoCollection<HandGrenade> grenadeDbCollection =
                this.database.getCollection(weaponCollectionName, HandGrenade.class);
        ArrayList<HandGrenade> grenadeList = grenadeDbCollection.find(eq("type", "HandGrenade")).into(new ArrayList<>());

        ArrayList<Weapon> combinedList = new ArrayList<>();

        combinedList.addAll(pistolList);
        combinedList.addAll(rifleList);
        combinedList.addAll(nukeList);
        combinedList.addAll(grenadeList);
        return combinedList;
    }
    //Update
    @Override
    public boolean updateOne(Long id, org.bson.conversions.Bson updater) {
        try {
            database
                    .getCollection(weaponCollectionName, Weapon.class)
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
            Weapon weapon = get(id);
            if (Objects.isNull(weapon)) throw new RepositoryException("No such weapon exists!");
            database
                    .getCollection(weaponCollectionName, Weapon.class)
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
