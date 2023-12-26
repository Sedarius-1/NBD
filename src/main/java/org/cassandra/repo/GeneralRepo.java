package org.cassandra.repo;

import org.cassandra.Enums;

import java.util.UUID;

public interface GeneralRepo<T> {

    public void createTable();

    public void insert(T entity);

    T select(Long entityId,
             Enums.classType type);

    void delete(T entity);

    void update(T entity);
}
