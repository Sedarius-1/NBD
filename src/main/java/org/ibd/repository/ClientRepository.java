package org.ibd.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;
import static org.ibd.repository.BongoConfig.*;

public class ClientRepository implements Repository<Client>, AutoCloseable {
    private final String clientCollectionName = "clientCollection";

    private final MongoClient mongoClient;

    private final MongoDatabase database;

    public ClientRepository() {
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
            if (name.equals(clientCollectionName)) {
                this.database.getCollection(name).drop();
                break;
            }
        }

        // Create test collection
        this.database.createCollection(clientCollectionName);
    }

    //Create
    @Override
    public void add(Client client) throws RepositoryException {
        try {
            MongoCollection<Client> testDBItemMongoCollection =
                    this.database.getCollection(clientCollectionName, Client.class);
            testDBItemMongoCollection.insertOne(client);
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }

    //Read
    @Override
    public Client get(Long id) throws RepositoryException {
        try {
            return this.database
                    .getCollection(clientCollectionName, Client.class)
                    .find(eq("clientId", id))
                    .into(new ArrayList<>())
                    .getFirst();
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }

    @Override
    public ArrayList<Client> find(org.bson.conversions.Bson bson) {
        return this.database
                .getCollection(clientCollectionName, Client.class)
                .find(bson)
                .into(new ArrayList<>());
    }

    @Override
    public ArrayList<Client> getAll() {
        MongoCollection<Client> testDBItemMongoCollection =
                this.database.getCollection(clientCollectionName, Client.class);
        return testDBItemMongoCollection.find().into(new ArrayList<>());
    }

    //Update
    @Override
    public boolean updateOne(Long id, org.bson.conversions.Bson updater) {
        try {
            database
                    .getCollection(clientCollectionName, Client.class)
                    .updateOne(eq("clientId", id), updater);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    //Delete
    @Override
    public void remove(Long id) throws RepositoryException {
        try {
            Client client = get(id);
            database
                    .getCollection(clientCollectionName, Client.class)
                    .deleteOne(eq("clientId", id));
        } catch (Exception ex) {
            throw new RepositoryException(ex.toString());
        }
    }


    @Override
    public void close() {
        this.mongoClient.close();
    }

}
