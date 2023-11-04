package org.ibd.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import lombok.Getter;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

@Getter

public class BongoConfig {
    static final ConnectionString connectionString = new ConnectionString(
            "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=mongo_repl");
    static final MongoCredential credential = MongoCredential.createCredential(
            "admin", "admin", "password".toCharArray());

    static final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());
}
