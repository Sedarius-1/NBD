package org.cassandra.repo;

public interface GeneralRepo<T> {

    void createTable();

    void insert(T entity);

    T select(Long entityId,
           String type);

    void delete(T entity);

    void update(T entity);
}
