package org.ibd.repository;

import org.ibd.exceptions.RepositoryException;

import java.util.List;


public interface Repository<T> {

    //Create
    void add(T t) throws RepositoryException;

    //Read
    T get(Long id) throws RepositoryException;

    List<T> find(org.bson.conversions.Bson bson);

    List<T> getAll();

    //Update
    boolean updateOne(Long id, org.bson.conversions.Bson updater);

    //Delete
    void remove(Long id) throws RepositoryException;
}
